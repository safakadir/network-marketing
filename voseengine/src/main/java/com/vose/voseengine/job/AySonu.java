package com.vose.voseengine.job;

import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.model.entity.JobArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AySonu {
    private static final Logger log = LoggerFactory.getLogger(AySonu.class);
    @Autowired
    private JobManager jobManager;

    @Scheduled(cron = "${scheduler.cronAySonu}") // at 00:00:05, on the 1st day, every month
    @Transactional
    public void run() {
        log.info("Cron job executed. 1st day of month, time 00:00:05.000");
        jobManager.produce(
                Job.Type.AY_SONU,
                new JobArgs(),
                null
        );
        jobManager.produce(
                Job.Type.CUZDANA_ISLE,
                new JobArgs(),
                null
        );
    }
}
