package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.JobProcessException;
import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.model.entity.*;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.repository.SiparisRepository;
import com.vose.voseengine.Util;
import com.vose.voseengine.job.helper.SponsorChain;
import com.vose.voseengine.job.helper.SponsorChainFormer;
import com.vose.voseengine.repository.AktiflikRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class OdemeOnayProcessor implements JobProcessor {
    private static final Logger log = LoggerFactory.getLogger(OdemeOnayProcessor.class);

    @Autowired
    private SiparisRepository siparisRepository;
    @Autowired
    private BayiRepository bayiRepository;
    @Autowired
    private AktiflikRepository aktiflikRepository;
    @Autowired
    private JobManager jobManager;

    @Autowired
    private SponsorChainFormer sponsorChainFormer;

    @Transactional
    @Override
    public void process(Job job) {
        Siparis siparis = getSiparisFromJob(job);
        SubBayi subBayi = siparis.getBayi();
        Bayi bayi = bayiRepository.findBayiById(subBayi.getId());


        if(bayi == null) {
            // Sipariş veren bayi değil. Bir şey yapmaya gerek yok. İşlem hatasız sonlandı.
            return;
        }

        executeAktiflikHesapla(bayi, siparis);

        //Paket tamamlama veya paket atlama
        executePaketOperation(job, siparis, bayi);

        SponsorChain sponsorChain = sponsorChainFormer.form(bayi);

        //8e kader sponsor silsilesine pv-cv ekle
        executePvCvAdding(job, siparis, sponsorChain);

        //Eğer bu alışverişe kadar kaydını tamamlamamış ise, aşağıdaki kazançlar hakedilmez.
        if(!bayi.getKayitDurum()) {
            return;
        }

        if(bayi.getSponsorId() == null) {
            return;
        }

        //8e kadar sponsor silsilesine bayi fark kazancı ekle
        executeBayiFarkKazanc(job, siparis, sponsorChain);

        //8e kadar sponsor silsilesine ekip alışveriş primi ekle
        executeEkipAlisverisPrimi(job, siparis, sponsorChain);
    }

    private Siparis getSiparisFromJob(Job job) {
        if(job.getSiparisIdArg() == null || job.getSiparisIdArg() <= 0) {
            throw new JobProcessException("Geçersiz(boş) siparisId. Job işlenmedi!");
        }
        Optional<Siparis> siparisOptional = siparisRepository.findById(job.getSiparisIdArg());
        if(!siparisOptional.isPresent()) {
            throw new JobProcessException("Verilen id ile sipariş bulunamadı. Job işlenmedi! Id:"+ job.getSiparisIdArg());
        }

        return siparisOptional.get();
    }

    private void executeAktiflikHesapla(Bayi bayi, Siparis siparis) {
        log.debug("Şu an: {}", new Date());
        if(siparis.getOdemeOnayTarih() == null) throw new RuntimeException("Sipariş odemeOnayTarih null!");
        Integer urunSayisi = siparisRepository.buAyUrunSayisi(bayi.getId(), Util.startOfMonthTolerated(siparis.getOdemeOnayTarih()));
        boolean isAktif = urunSayisi != null && urunSayisi >= bayi.getKariyer().getAktiflikSarti();
        if(isAktif) {
            String monthKey = Util.monthKeyTolerated(siparis.getOdemeOnayTarih());
            Aktiflik aktiflik = aktiflikRepository.findByBayiIdAndAy(bayi.getId(), monthKey);
            if(aktiflik == null) aktiflik = new Aktiflik();
            aktiflik.setBayiId(bayi.getId());
            aktiflik.setAy(monthKey);
            aktiflik.setUrunSayisi(urunSayisi);
            aktiflikRepository.save(aktiflik);
        }
    }

    private void executePaketOperation(Job job, Siparis siparis, Bayi bayi) {
        log.debug("Paket işlemleri kontrol ediliyor. SiparisId:{}", siparis.getId());
        if(bayi.getKayitDurum()) {
            // Kayıt tamamlanmış bayi
            if(siparis.isPaketAlisveris()) {
                // Eğer hala başlangıç paketlerinde ise, sipariş tutarı fark tutarı kadar mı diye bakar
                Kariyer nextKariyer = bayiRepository.getNextKariyer(bayi.getId());
                if (nextKariyer != null && nextKariyer.getBaslangicPaket()) {
                    log.info("Paket atlama kontrolü yapılıyor. {}", bayi);
                    int fark = nextKariyer.getBaslangicPaketTutar() - bayi.getKariyer().getBaslangicPaketTutar();
                    if (siparis.getUrunTutar() >= fark) {
                        jobManager.produce(
                                Job.Type.KARIYER_ATLA,
                                new JobArgs.Builder().setBayiId(bayi.getId()).build(),
                                job
                        );
                    }
                }
            }
        }
        else {
            // Kayıt tamamlanmamış bayi
            // Eğer kayıt tamamlanmamış ise, bu sipariş ile paket alışverişini tamamlayıp kaydını tamamlamaya bakar
            if(!bayi.getKariyer().getBaslangicPaket()) {
                throw new JobProcessException("Kaydı tamamlanmamış bayi, başlangıç paketinde değil. Bayi:"+bayi.toString());
            }
            log.info("Paket onay kontrolü yapılıyor. {}", bayi);
            int paketTutar = bayi.getKariyer().getBaslangicPaketTutar();
            if(siparis.getUrunTutar() >= paketTutar) {
                jobManager.produce(
                        Job.Type.KAYIT_TAMAMLA,
                        new JobArgs.Builder().setBayiId(bayi.getId()).build(),
                        job
                );
            }
        }
    }

    private void executePvCvAdding(Job job, Siparis siparis, SponsorChain sponsorChain) {
        log.debug("Pv-cv ekleme jobları üretiliyor. SiparisId:{}", siparis.getId());
        for(Bayi bayi : sponsorChain.getSponsors()) {
            jobManager.produce(
                    Job.Type.PUAN_EKLE,
                    new JobArgs.Builder()
                            .setBayiId(bayi.getId())
                            .setAltBayiId(sponsorChain.getAltBayiOf(bayi).getId())
                            .setPv(siparis.getToplamPv())
                            .setCv(siparis.getToplamCv())
                            .build(),
                    job
            );
        }
    }

    private void executeBayiFarkKazanc(Job job, Siparis siparis, SponsorChain sponsorChain) {
        Bayi bayi = sponsorChain.getBayi();
        for(Bayi sponsor : sponsorChain.getSponsors()) {
            if(Util.isNullOrEmpty(sponsor.getKariyer().getFarkKazanci())) continue;
            String[] yuzdeDagilim = sponsor.getKariyer().getFarkKazanci().split(",");
            if(yuzdeDagilim.length == 0) continue;
            int derinlik = sponsorChain.getDerinlikOf(sponsor);
            if(derinlik > yuzdeDagilim.length) continue;
            int yuzde = Integer.parseInt(yuzdeDagilim[derinlik-1]);
            if(yuzde == 0) continue;

            int farkYuzde = sponsor.getKariyer().getAlisverisIndirimi() - bayi.getKariyer().getAlisverisIndirimi();
            double farkMiktar = siparis.getUrunTutar()/100.0*farkYuzde;
            double farkKazanci = farkMiktar/100.0*yuzde;

            jobManager.produce(
                    Job.Type.KAZANC_EKLE,
                    new JobArgs.Builder()
                            .setBayiId(sponsor.getId())
                            .setAltBayiId(bayi.getId())
                            .setTutar(Util.round(farkKazanci, 2))
                            .setKazancTuru(Kazanc.Tur.SPONSOR_FARK_KAZANCI)
                            .build(),
                    job
            );
        }
    }

    private void executeEkipAlisverisPrimi(Job job, Siparis siparis, SponsorChain sponsorChain) {
        for(Bayi bayi : sponsorChain.getSponsors()) {
            log.debug("Ekip alisveriş primleri hesaplanıyor. SiparisId:{} Sponsor:{}", siparis.getId(), bayi.getId());
            if(Util.isNullOrEmpty(bayi.getKariyer().getEkipAlisveris())) continue;
            String[] yuzdeDagilim = bayi.getKariyer().getEkipAlisveris().split(",");
            if(yuzdeDagilim.length == 0) continue;
            int derinlik = sponsorChain.getDerinlikOf(bayi);
            if(derinlik > yuzdeDagilim.length) continue;
            int yuzde = Integer.parseInt(yuzdeDagilim[derinlik-1]);
            if(yuzde == 0) continue;
            double ekipAlisverisPrimi = siparis.getUrunTutar()/100.0*yuzde;
            jobManager.produce(
                    Job.Type.KAZANC_EKLE,
                    new JobArgs.Builder()
                        .setBayiId(bayi.getId())
                        .setAltBayiId(sponsorChain.getBayi().getId())
                        .setTutar(Util.round(ekipAlisverisPrimi, 2))
                        .setKazancTuru(Kazanc.Tur.EKIP_ALISVERIS_PRIMI)
                        .build(),
                    job
            );
        }
    }
}
