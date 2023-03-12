package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.job.helper.PvProjectionSummary;
import com.vose.voseengine.model.entity.Bayi;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.model.entity.Kariyer;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.repository.PuanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KariyerAtlaProcessor implements JobProcessor {
    private static final Logger log = LoggerFactory.getLogger(KariyerAtlaProcessor.class);

    @Autowired
    private BayiRepository bayiRepository;
    @Autowired
    private PuanRepository puanRepository;
    @Autowired
    private JobManager jobManager;

    @Transactional
    @Override
    public void process(Job job) {
        Long bayiId = job.getBayiIdArg();
        Kariyer next = bayiRepository.getNextKariyer(bayiId);
        Bayi bayi = bayiRepository.findById(bayiId).get();
        if(next == null) {
            log.warn("Anormal durum: En üst kariyerdeki bayi ile KARIYER_ATLA geldi. {}, {}", job, bayi);
            return;
        }
        bayi.setKariyer(next);
        bayiRepository.save(bayi);

        //Kariyer atladığı için kullanılan pv puanları düşülüyor
        if(!next.getBaslangicPaket() && job.getPvCollectionArg() != null) {
            for(PvProjectionSummary pvo : job.getPvCollectionArg().getSummaryDistribution()) {
                puanRepository.pvDus(bayiId, pvo.getKaynakBayiId(), pvo.getAlinanPv());
            }
        }

    }
}
