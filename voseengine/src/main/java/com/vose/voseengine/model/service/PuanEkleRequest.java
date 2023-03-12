package com.vose.voseengine.model.service;

public class PuanEkleRequest {
    private Long bayiId;
    private Long altBayiId;
    private double pv;
    private double cv;

    public Long getBayiId() {
        return bayiId;
    }

    public void setBayiId(Long bayiId) {
        this.bayiId = bayiId;
    }

    public Long getAltBayiId() {
        return altBayiId;
    }

    public void setAltBayiId(Long altBayiId) {
        this.altBayiId = altBayiId;
    }

    public double getPv() {
        return pv;
    }

    public void setPv(double pv) {
        this.pv = pv;
    }

    public double getCv() {
        return cv;
    }

    public void setCv(double cv) {
        this.cv = cv;
    }
}
