package com.vose.voseengine.job;

import com.vose.voseengine.model.entity.Job;

public interface JobProcessor {
    void process(Job job);
}
