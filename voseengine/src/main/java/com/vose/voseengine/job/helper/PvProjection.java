package com.vose.voseengine.job.helper;

public class PvProjection extends PvProjectionSummary {
    private double toplamPv;
    private double yuzde;

    public PvProjection(long bayiId, double alinanPv, double toplamPv, double yuzde) {
        super(bayiId, alinanPv);
        this.toplamPv = toplamPv;
        this.yuzde = yuzde;;
    }

    public double getToplamPv() {
        return toplamPv;
    }

    public double getYuzde() {
        return yuzde;
    }
}
