package com.vose.voseengine.job.processor;

import com.vose.voseengine.model.entity.*;
import com.vose.voseengine.repository.*;
import com.vose.voseengine.Util;
import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.job.helper.SponsorChain;
import com.vose.voseengine.job.helper.SponsorChainFormer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AySonuBayiProcessor implements JobProcessor {
    private static final Logger log = LoggerFactory.getLogger(AySonuBayiProcessor.class);

    @Autowired
    private BayiRepository bayiRepository;
    @Autowired
    private AktiflikRepository aktiflikRepository;
    @Autowired
    private PuanRepository puanRepository;
    @Autowired
    private KazancRepository kazancRepository;
    @Autowired
    private CuzdanRepository cuzdanRepository;
    @Autowired
    private KariyerRepository kariyerRepository;
    @Autowired
    private SiparisRepository siparisRepository;
    @Autowired
    private AppSettingRepository appSettingRepository;
    @Autowired
    private JobManager jobManager;

    @Autowired
    private SponsorChainFormer sponsorChainFormer;

    @Transactional
    @Override
    public void process(Job job) {
        String monthKey = job.getMonthKeyArg();
        Date startOfMonth = Util.startOfMonthByKey(monthKey);
        Bayi bayi = bayiRepository.findById(job.getBayiIdArg()).get();

        Aktiflik aktiflik = aktiflikRepository.findByBayiIdAndAy(job.getBayiIdArg(), monthKey);
        if(aktiflik == null) {
            handleInaktifBayi(job, bayi, startOfMonth);
        }
        else {
            handleAktifBayi(job, bayi, startOfMonth, monthKey);
        }
    }

    private void handleInaktifBayi(Job job, Bayi bayi, Date startOfMonth) {
        //puan sıfırlama
        puanRepository.puanSifirla(bayi.getId());
        //kazanç sıfırlama
        kazancRepository.kazancSifirla(bayi.getId(), startOfMonth);
        //ay başı kariyerine döndürme
        if(bayi.getAybasiKariyerId() != null) {
            Kariyer aybasiKariyer = kariyerRepository.findById(bayi.getAybasiKariyerId()).get();
            bayi.setKariyer(aybasiKariyer);
            bayiRepository.save(bayi);
        }
    }

    private void handleAktifBayi(Job job, Bayi bayi, Date startOfMonth, String monthKey) {
        if(!bayi.getKayitDurum()) {
            log.info("Bayi aktif ancak, ay sonunda hala kaydını tamamlamamış. {}", bayi.getIdentity());
            return;
        }

        // bayi aktiflik primi
        executeBayiAktiflikPrimi(job, bayi, startOfMonth);

        // lider destek primi
        executeLiderDestekPrimi(job, bayi);

        List<Bayi> kollarOrdered = bayiRepository.findBySponsorIdOrderByKolSiraAsc(bayi.getId());
        // lider çıkarma primi, aybaşı kariyer setleme
        executeLiderCikarmaPrimi(job, bayi, kollarOrdered);

        // binary kazancı
        double binaryKazanc = executeBinaryKazanci(job, bayi, kollarOrdered);

        // ekip kazanç primi
        if(BigDecimal.valueOf(binaryKazanc).compareTo(BigDecimal.ZERO) > 0) {
            SponsorChain sponsorChain = sponsorChainFormer.form(bayi);
            executeEkipKazancPrimi(job, binaryKazanc, sponsorChain);
        }
    }

    private void executeBayiAktiflikPrimi(Job job, Bayi bayi, Date startOfMonth) {
        if(bayi.getKariyer().getBayiAktiflikPuan() == null || bayi.getKariyer().getBayiAktiflikPuan() == 0) return;
        if(bayi.getKariyer().getBayiAktiflikKazanc() == null || bayi.getKariyer().getBayiAktiflikKazanc() == 0) return;
        Double aktifPv = siparisRepository.direkBayiBuAyPv(bayi.getId(), startOfMonth);
        if(aktifPv != null && BigDecimal.valueOf(aktifPv).compareTo(BigDecimal.valueOf(bayi.getKariyer().getBayiAktiflikPuan())) >= 0) {
            jobManager.produce(
                    Job.Type.KAZANC_EKLE,
                    new JobArgs.Builder()
                            .setBayiId(bayi.getId())
                            .setTutar(bayi.getKariyer().getBayiAktiflikKazanc().doubleValue())
                            .setKazancTuru(Kazanc.Tur.BAYI_AKTIFLIK_PRIMI)
                            .build(),
                    job
            );
        }
    }

    private void executeLiderDestekPrimi(Job job, Bayi bayi) {
        if(bayi.getKariyer().getLiderDestek() != null && bayi.getKariyer().getLiderDestek() > 0) {
            jobManager.produce(
                    Job.Type.KAZANC_EKLE,
                    new JobArgs.Builder()
                            .setBayiId(bayi.getId())
                            .setTutar(bayi.getKariyer().getLiderDestek().doubleValue())
                            .setKazancTuru(Kazanc.Tur.LIDER_DESTEK_PRIMI)
                            .build(),
                    job
            );
        }
    }

    private void executeLiderCikarmaPrimi(Job job, Bayi bayi, List<Bayi> kollarOrdered) {
        for(Bayi kol : kollarOrdered) {
            if(kol.getAybasiKariyerId().longValue() == kol.getKariyer().getId().longValue()) continue;
            Integer liderCikarma = kol.getKariyer().getLiderCikarma();
            if(liderCikarma != null && liderCikarma > 0) {
                jobManager.produce(
                        Job.Type.KAZANC_EKLE,
                        new JobArgs.Builder()
                                .setBayiId(bayi.getId())
                                .setAltBayiId(kol.getId())
                                .setTutar(liderCikarma.doubleValue())
                                .setKazancTuru(Kazanc.Tur.LIDER_CIKARMA_PRIMI)
                                .build(),
                        job
                );
            }
            kol.setAybasiKariyerId(kol.getKariyer().getId());
            bayiRepository.save(kol);
        }
    }

    private double executeBinaryKazanci(Job job, Bayi bayi, List<Bayi> kollarOrdered) {
        int totalMatchAmount = 0;
        int cvMatchAmount = getCvMatchAmount();
        for(int i = 0; i < kollarOrdered.size()-1; i += 2) {
            long kolId1 = kollarOrdered.get(i).getId();
            long kolId2 = kollarOrdered.get(i+1).getId();
            BayiPuan puan1 = puanRepository.findByBayiIdAndAltBayiId(bayi.getId(), kolId1);
            BayiPuan puan2 = puanRepository.findByBayiIdAndAltBayiId(bayi.getId(), kolId2);
            if(puan1 == null || puan2 == null) continue;
            int matchCount = (int)(Math.min(puan1.getCv(), puan2.getCv())/cvMatchAmount);
            if(matchCount == 0) continue;
            totalMatchAmount += matchCount*cvMatchAmount;
            puanRepository.cvDus(bayi.getId(), kolId1, (double)totalMatchAmount);
            puanRepository.cvDus(bayi.getId(), kolId2, (double)totalMatchAmount);
        }
        if(totalMatchAmount == 0) return 0;
        double binaryKazanci = totalMatchAmount/100.0*bayi.getKariyer().getBinaryEslesme();
        jobManager.produce(
                Job.Type.KAZANC_EKLE,
                new JobArgs.Builder()
                        .setBayiId(bayi.getId())
                        .setTutar(binaryKazanci)
                        .setKazancTuru(Kazanc.Tur.BINARY_KAZANCI)
                        .build(),
                job
        );
        return binaryKazanci;
    }

    private void executeEkipKazancPrimi(Job job, double binaryKazanc, SponsorChain sponsorChain) {
        for(Bayi bayi : sponsorChain.getSponsors()) {
            if(Util.isNullOrEmpty(bayi.getKariyer().getEkipKazanc())) continue;
            String[] yuzdeDagilim = bayi.getKariyer().getEkipKazanc().split(",");
            if(yuzdeDagilim.length == 0) continue;
            int derinlik = sponsorChain.getDerinlikOf(bayi);
            if(derinlik > yuzdeDagilim.length) continue;
            int yuzde = Integer.parseInt(yuzdeDagilim[derinlik-1]);
            if(yuzde == 0) continue;
            double ekipKazancPrimi = binaryKazanc/100.0*yuzde;
            jobManager.produce(
                    Job.Type.KAZANC_EKLE,
                    new JobArgs.Builder()
                            .setBayiId(bayi.getId())
                            .setAltBayiId(sponsorChain.getBayi().getId())
                            .setTutar(ekipKazancPrimi)
                            .setKazancTuru(Kazanc.Tur.EKIP_KAZANC_PRIMI)
                            .build(),
                    job
            );
        }
    }

    private int getCvMatchAmount() {
        Optional<AppSetting> settingOptional = appSettingRepository.findById("cvMatchAmount");
        if(!settingOptional.isPresent() || settingOptional.get().getInt() == null) {
            log.warn("APP SETTING cvMatchAmount NOT SET. Using default value 300!");
            return 300;
        }
        return settingOptional.get().getInt();
    }
}
