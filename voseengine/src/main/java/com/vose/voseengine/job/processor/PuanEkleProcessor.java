package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.model.entity.BayiPuan;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.model.entity.JobArgs;
import com.vose.voseengine.repository.PuanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PuanEkleProcessor implements JobProcessor {
    private static final Logger log = LoggerFactory.getLogger(PuanEkleProcessor.class);
    @Autowired
    private PuanRepository puanRepository;
    @Autowired
    private JobManager jobManager;

    @Transactional
    @Override
    public void process(Job job) {
        BayiPuan puan = puanRepository.findByBayiIdAndAltBayiId(job.getBayiIdArg(), job.getAltBayiIdArg());
        if(puan == null) {
            puan = new BayiPuan();
            puan.setBayiId(job.getBayiIdArg());
            puan.setAltBayiId(job.getAltBayiIdArg());
            puan.setTarih(new Date());
            puanRepository.save(puan);
            log.info("Yeni BayiPuan kaydı oluşturuldu. {}", puan);
        }
        puanRepository.puanEkle(
                job.getBayiIdArg(),
                job.getAltBayiIdArg(),
                job.getPvArg(),
                job.getCvArg()
        );
        jobManager.produce(
                Job.Type.KARIYER_CHECK,
                new JobArgs.Builder().setBayiId(job.getBayiIdArg()).build(),
                job
        );
    }
}
