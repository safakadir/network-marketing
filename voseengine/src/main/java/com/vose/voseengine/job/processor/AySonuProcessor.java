package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.Util;
import com.vose.voseengine.model.entity.JobArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AySonuProcessor implements JobProcessor {
    @Autowired
    private BayiRepository bayiRepository;
    @Autowired
    private JobManager jobManager;

    @Transactional
    @Override
    public void process(Job job) {
        String monthKey = Util.monthKeyTolerated(job.getCreateDate());
        List<Long> bayiIds = bayiRepository.findAllBayiIds(Sort.by(Sort.Order.desc("derinlik"), Sort.Order.asc("sponsorId"), Sort.Order.asc("kolSira")));
        for(Long bayiId : bayiIds) {
            jobManager.produce(
                    Job.Type.AY_SONU_BAYI,
                    new JobArgs.Builder()
                            .setBayiId(bayiId)
                            .setMonthKey(monthKey)
                            .build(),
                    job
            );
        }
    }
}
