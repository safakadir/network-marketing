package com.vose.voseengine.job.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PvCollection {

    private Long bayiId;
    private transient List<PvProjection> distribution;
    private List<PvProjectionSummary> summaryDistribution;
    private int hedefPuan;
    private double totalObtained = -1;

    public PvCollection(Long bayiId, List<PvProjection> distribution, int hedefPuan) {
        this.bayiId = bayiId;
        this.distribution = distribution;
        this.summaryDistribution = new ArrayList<>();
        for(PvProjection o : distribution) {
            this.summaryDistribution.add(new PvProjectionSummary(o.getKaynakBayiId(), o.getAlinanPv()));
        }
        this.hedefPuan = hedefPuan;
        this.totalObtained = getTotalObtained();
    }

    public double getTotalObtained() {
        if(totalObtained >= 0) return totalObtained;
        double sum = 0;
        for(PvProjection projection : distribution) {
            sum += projection.getAlinanPv();
        }
        return sum;
    }

    public boolean isHedefAchieved() {
        return BigDecimal.valueOf(getTotalObtained()).compareTo(BigDecimal.valueOf(hedefPuan)) >= 0;
    }

    public Long getBayiId() {
        return bayiId;
    }

    public List<PvProjection> getDistribution() {
        return distribution;
    }

    public List<PvProjectionSummary> getSummaryDistribution() {
        return summaryDistribution;
    }

    public int getHedefPuan() {
        return hedefPuan;
    }

    @Override
    public String toString() {
        return "PvCollection{" +
                "bayiId=" + bayiId +
                ", summaryDistribution=" + summaryDistribution +
                ", totalObtained=" + totalObtained +
                ", hedefAchieved=" + isHedefAchieved() +
                '}';
    }
}
