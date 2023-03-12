package com.vose.voseengine.job.helper;

import com.vose.voseengine.model.entity.BayiPuan;

import java.util.List;

public interface PvCollector {
    PvCollection collect(long bayiId, List<BayiPuan> altPuanlar, int hedefPuan, double maxSingleCoef);
}
