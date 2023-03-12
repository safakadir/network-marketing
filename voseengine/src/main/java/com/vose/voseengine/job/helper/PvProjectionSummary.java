package com.vose.voseengine.job.helper;

public class PvProjectionSummary {
    protected long kaynakBayiId;
    protected double alinanPv;

    public PvProjectionSummary() {
    }

    public PvProjectionSummary(long kaynakBayiId, double alinanPv) {
        this.kaynakBayiId = kaynakBayiId;
        this.alinanPv = alinanPv;
    }

    public long getKaynakBayiId() {
        return kaynakBayiId;
    }

    public double getAlinanPv() {
        return alinanPv;
    }

    @Override
    public String toString() {
        return "{" +
                kaynakBayiId +
                ":" + alinanPv +
                '}';
    }
}


