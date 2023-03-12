package com.vose.voseengine.job;

import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.model.entity.JobArgs;

public interface JobManager {
    Job run();
    Job consume(Job job);
    Job produce(Job job);
    Job produce(Job.Type type, JobArgs args, Job parentJob);
}
