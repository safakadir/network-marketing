package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.job.helper.PvCollector;
import com.vose.voseengine.model.entity.*;
import com.vose.voseengine.repository.AppSettingRepository;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.job.helper.PvCollection;
import com.vose.voseengine.repository.PuanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class KariyerCheckProcessor implements JobProcessor {
    private static final Logger log = LoggerFactory.getLogger(KariyerCheckProcessor.class);

    @Autowired
    private BayiRepository bayiRepository;
    @Autowired
    private PuanRepository puanRepository;
    @Autowired
    private AppSettingRepository appSettingRepository;
    @Autowired
    private JobManager jobManager;

    @Autowired
    private PvCollector pvCollector;

    @Transactional
    @Override
    public void process(Job job) {
        Kariyer next = bayiRepository.getNextKariyer(job.getId());
        if(next == null || next.getBaslangicPaket()) return;
        int hedefPuan = next.getKariyerPuan();
        long bayiId = job.getBayiIdArg();
        List<BayiPuan> altPuanlar = puanRepository.findByBayiId(bayiId);
        double maxSingleCoef = getMaxSingleCoef();
        PvCollection pvCollection = pvCollector.collect(bayiId, altPuanlar, hedefPuan, maxSingleCoef);
        if(pvCollection.isHedefAchieved()) {
            log.info("Bayi pv toplayarak kariyer atlıyor. {}", pvCollection);
            jobManager.produce(
                    Job.Type.KARIYER_ATLA,
                    new JobArgs.Builder()
                        .setBayiId(job.getBayiIdArg())
                        .setPvCollection(pvCollection)
                        .build(),
                    job
            );
        }
    }

    private double getMaxSingleCoef() {
        Optional<AppSetting> settingOptional = appSettingRepository.findById("pvMaxSinglePercentage");
        if(!settingOptional.isPresent() || settingOptional.get().getInt() == null) {
            log.warn("APP SETTING pvMaxSinglePercentage NOT SET. Using default value 40!");
            return 0.4;
        }
        return settingOptional.get().getInt()/100.0;
    }
}
