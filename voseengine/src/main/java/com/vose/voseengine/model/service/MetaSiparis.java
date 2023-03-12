package com.vose.voseengine.model.service;

import com.vose.voseengine.model.entity.Adres;

import java.util.List;

public class MetaSiparis {
    private Long bayiId;
    private String isimSoyisim;
    private Adres adres;
    private double toplamFiyat;
    private double toplamFiyatIndirimsiz;
    private double odenecekTutar;
    private int indirim;
    private boolean paketAlisveris;
    private List<SepetItem> urunler;
    private String odemeYontemi;

    public Long getBayiId() {
        return bayiId;
    }

    public void setBayiId(Long bayiId) {
        this.bayiId = bayiId;
    }

    public String getIsimSoyisim() {
        return isimSoyisim;
    }

    public void setIsimSoyisim(String isimSoyisim) {
        this.isimSoyisim = isimSoyisim;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public double getToplamFiyat() {
        return toplamFiyat;
    }

    public void setToplamFiyat(double toplamFiyat) {
        this.toplamFiyat = toplamFiyat;
    }

    public double getToplamFiyatIndirimsiz() {
        return toplamFiyatIndirimsiz;
    }

    public void setToplamFiyatIndirimsiz(double toplamFiyatIndirimsiz) {
        this.toplamFiyatIndirimsiz = toplamFiyatIndirimsiz;
    }

    public double getOdenecekTutar() {
        return odenecekTutar;
    }

    public void setOdenecekTutar(double odenecekTutar) {
        this.odenecekTutar = odenecekTutar;
    }

    public int getIndirim() {
        return indirim;
    }

    public void setIndirim(int indirim) {
        this.indirim = indirim;
    }

    public boolean isPaketAlisveris() {
        return paketAlisveris;
    }

    public void setPaketAlisveris(boolean paketAlisveris) {
        this.paketAlisveris = paketAlisveris;
    }

    public List<SepetItem> getUrunler() {
        return urunler;
    }

    public void setUrunler(List<SepetItem> urunler) {
        this.urunler = urunler;
    }

    public String getOdemeYontemi() {
        return odemeYontemi;
    }

    public void setOdemeYontemi(String odemeYontemi) {
        this.odemeYontemi = odemeYontemi;
    }
}
