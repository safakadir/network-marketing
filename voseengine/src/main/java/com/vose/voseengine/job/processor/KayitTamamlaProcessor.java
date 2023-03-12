package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.model.entity.*;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.Util;
import com.vose.voseengine.job.helper.SponsorChain;
import com.vose.voseengine.job.helper.SponsorChainFormer;
import com.vose.voseengine.repository.AktiflikRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KayitTamamlaProcessor implements JobProcessor {
    private static final Logger log = LoggerFactory.getLogger(KayitTamamlaProcessor.class);

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
        Bayi bayi = bayiRepository.findById(job.getBayiIdArg()).get();
        bayi.setKayitDurum(true);
        bayi.setAybasiKariyerId(bayi.getKariyer().getId());
        bayiRepository.save(bayi);

        executeEnsureAktiflik(bayi);

        if(bayi.getSponsorId() != null) {
            SponsorChain sponsorChain = sponsorChainFormer.form(bayi);
            executeSponsorFarkKazanci(job, bayi.getKariyer(), sponsorChain);
            executeSponsorPrimi(job, bayi.getKariyer(), sponsorChain);
        }
    }

    private void executeEnsureAktiflik(Bayi bayi) {
        String monthKey = Util.monthKeyTolerated();
        Aktiflik aktiflik = aktiflikRepository.findByBayiIdAndAy(bayi.getId(), monthKey);
        if(aktiflik == null) aktiflik = new Aktiflik();
        aktiflik.setBayiId(bayi.getId());
        aktiflik.setAy(monthKey);
        aktiflikRepository.save(aktiflik);
    }

    private void executeSponsorFarkKazanci(Job job, Kariyer paket, SponsorChain sponsorChain) {
        Bayi bayi = sponsorChain.getBayi();
        for(Bayi sponsor : sponsorChain.getSponsors()) {
            if(Util.isNullOrEmpty(sponsor.getKariyer().getFarkKazanci())) continue;
            String[] yuzdeDagilim = sponsor.getKariyer().getFarkKazanci().split(",");
            if(yuzdeDagilim.length == 0) continue;
            int derinlik = sponsorChain.getDerinlikOf(sponsor);
            if(derinlik > yuzdeDagilim.length) continue;
            int yuzde = Integer.parseInt(yuzdeDagilim[derinlik-1]);
            if(yuzde == 0) continue;

            int farkYuzde = sponsor.getKariyer().getAlisverisIndirimi() - paket.getAlisverisIndirimi();
            double farkMiktar = paket.getBaslangicPaketTutar()/100.0*farkYuzde;
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

    private void executeSponsorPrimi(Job job, Kariyer paket, SponsorChain sponsorChain) {
        for(Bayi bayi : sponsorChain.getSponsors()) {
            log.debug("Sponsor primi hesaplanıyor. Paket:{}, Sponsor:{}", paket.getBaslangicPaketTutar(), bayi.getId());
            if(Util.isNullOrEmpty(bayi.getKariyer().getSponsorPrimi())) continue;
            String[] yuzdeDagilim = bayi.getKariyer().getSponsorPrimi().split(",");
            if(yuzdeDagilim.length == 0) continue;
            int derinlik = sponsorChain.getDerinlikOf(bayi);
            if(derinlik > yuzdeDagilim.length) continue;
            int yuzde = Integer.parseInt(yuzdeDagilim[derinlik-1]);
            if(yuzde == 0) continue;
            double sponsorPrimi = paket.getBaslangicPaketTutar()/100.0*yuzde;
            jobManager.produce(
                    Job.Type.KAZANC_EKLE,
                    new JobArgs.Builder()
                            .setBayiId(bayi.getId())
                            .setAltBayiId(sponsorChain.getBayi().getId())
                            .setTutar(Util.round(sponsorPrimi, 2))
                            .setKazancTuru(Kazanc.Tur.SPONSOR_PRIMI)
                            .build(),
                    job
            );
        }
    }
}
