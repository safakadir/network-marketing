package com.vose.voseengine.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="ar_bayiler")
public class SubBayi {

    @Id
    private Long id;
    private String isim;
    private String soyisim;
    private Long sponsorId;
    @ManyToOne
    private Kariyer kariyer;
    private int derinlik;
    private int kolSira;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getIsim() {
        return isim;
    }

    @JsonIgnore
    public void setIsim(String isim) {
        this.isim = isim;
    }

    @JsonIgnore
    public String getSoyisim() {
        return soyisim;
    }

    @JsonIgnore
    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    @JsonIgnore
    public Long getSponsorId() {
        return sponsorId;
    }

    @JsonIgnore
    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    @JsonIgnore
    public Kariyer getKariyer() {
        return kariyer;
    }

    @JsonIgnore
    public void setKariyer(Kariyer kariyer) {
        this.kariyer = kariyer;
    }

    public int getDerinlik() {
        return derinlik;
    }

    public void setDerinlik(int derinlik) {
        this.derinlik = derinlik;
    }

    public int getKolSira() {
        return kolSira;
    }

    public void setKolSira(int kolSira) {
        this.kolSira = kolSira;
    }

    public String getIsimSoyisim() {
        return isim+" "+soyisim;
    }

    public String getIdentity() {
        return String.format("%d - %s", id, getIsimSoyisim());
    }

    public Long getKariyerId() {
        return kariyer.getId();
    }

    public String getKariyerAdi() {
        return kariyer.getKariyerAdi();
    }

    public static SubBayi of(Bayi bayi) {
        SubBayi result = new SubBayi();
        result.id = bayi.getId();
        result.isim = bayi.getIsim();
        result.soyisim = bayi.getSoyisim();
        result.kariyer = bayi.getKariyer();
        result.derinlik = bayi.getDerinlik();
        result.kolSira = bayi.getKolSira();
        result.sponsorId = bayi.getSponsorId();
        return result;
    }
}
