package com.vose.voseengine.model.service;

import com.vose.voseengine.model.entity.Bayi;

public class BayiContainer {
    private Bayi bayi;
    private String sifre;

    public Bayi getBayi() {
        return bayi;
    }

    public void setBayi(Bayi bayi) {
        this.bayi = bayi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}
