package com.vose.voseengine.job;

import com.vose.voseengine.job.processor.ProcessorFactory;
import com.vose.voseengine.model.entity.FailLog;
import com.vose.voseengine.repository.JobRepository;
import com.vose.voseengine.Util;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.model.entity.JobArgs;
import com.vose.voseengine.repository.FailLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@Component
public class JobManagerImpl implements JobManager {
    private static final Logger log = LoggerFactory.getLogger(JobManagerImpl.class);

    @Value("${scheduler.noJobSleep}")
    private long noJobSleep;
    @Value("${scheduler.failedJobSleep}")
    private long failedJobSleep;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private FailLogRepository failLogRepository;

    @Autowired
    private ProcessorFactory processorFactory;

    @Override
    @Scheduled(fixedDelayString = "${scheduler.jobProcessDelay}", initialDelayString = "${scheduler.jobProcessInitDelay}")
    public Job run() {
        try {
            Job job = jobRepository.findTopByProcessedFalseOrderByRootIdAscIdAsc();
            if(job == null) {
                Util.safeSleep(noJobSleep);
                return null; // no job to be processed
            }
            Job processedJob = consume(job);
            return processedJob;
        }
        catch (Exception e) {
            log.error("Couldn't fetch next job!", e);
            Util.safeSleep(failedJobSleep);
            return null;
        }
    }

    @Override
    public Job produce(Job job) {
        try {
            log.info("Saving new job. {}", job);
            job = jobRepository.save(job);

            return job;
        }
        catch (Exception e) {
            log.error("New job couldn't be saved! {}", job, e);
            return null;
        }
    }

    @Override
    public Job produce(Job.Type type, JobArgs args, Job parentJob) {
        return produce(Job.create(type, args, parentJob));
    }

    @Override
    public Job consume(Job job) {
        try {
            JobProcessor processor = processorFactory.getProcessorFor(job);
            if(processor == null) {
                throw new JobProcessException("Couldn't find a JobProccessor for "+job.getType());
            }
            log.info("Processing {}", job);
            processor.process(job);
            log.info("Successfully processed. {}", job);
            job.setProcessed(true);
            job.setProcessDate(new Date());
            job.setSuccess(true);
            job = jobRepository.save(job);
            return job;
        }
        catch (Exception e) {
            log.error("Job process failed. {}", job, e);
            job.setProcessed(true);
            job.setProcessDate(new Date());
            job.setSuccess(false);
            FailLog failLog = saveFailLog(e);
            job.setFailLog(failLog);
            try {
                return jobRepository.save(job);
            }
            catch (Exception e2) {
                log.error("The job has prossed but unsuccessfull cannot be updated at database!", e2);
                return null;
            }
        }
    }

    private FailLog saveFailLog(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        FailLog failLog = new FailLog();
        failLog.setMessage(exceptionAsString);
        return failLogRepository.save(failLog);
    }

}
