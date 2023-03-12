package com.vose.voseengine.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ar_kazanclar")
public class Kazanc implements Identable<Long>, BayiRelatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private SubBayi bayi;
    @ManyToOne
    private SubBayi altBayi;
    private Double miktar;
    @Enumerated(EnumType.STRING)
    private Tur kazancTuru;
    private String aciklama;
    @CreationTimestamp
    private Date tarih;
    private boolean cuzdanaIslendi;
    private boolean silindi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public Long getBayiId() {
        return bayi.getId();
    }

    public SubBayi getBayi() {
        return bayi;
    }

    public void setBayi(SubBayi bayi) {
        this.bayi = bayi;
    }

    public SubBayi getAltBayi() {
        return altBayi;
    }

    public void setAltBayi(SubBayi altBayi) {
        this.altBayi = altBayi;
    }

    public Double getMiktar() {
        return miktar;
    }

    public void setMiktar(Double miktar) {
        this.miktar = miktar;
    }

    public Tur getKazancTuru() {
        return kazancTuru;
    }

    public void setKazancTuru(Tur kazancTuru) {
        this.kazancTuru = kazancTuru;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public boolean getCuzdanaIslendi() {
        return cuzdanaIslendi;
    }

    public void setCuzdanaIslendi(boolean cuzdanaIslendi) {
        this.cuzdanaIslendi = cuzdanaIslendi;
    }

    public boolean getSilindi() {
        return silindi;
    }

    public void setSilindi(boolean silindi) {
        this.silindi = silindi;
    }

    @Override
    public String toString() {
        return "Kazanc{" +
                "id=" + id +
                ", bayi_id=" + bayi.getId() +
                ", miktar=" + miktar +
                ", kazancTuru='" + kazancTuru + '\'' +
                '}';
    }

    public enum Tur {
        EKIP_ALISVERIS_PRIMI,
        BINARY_KAZANCI,
        EKIP_KAZANC_PRIMI,
        BAYI_FARK_KAZANCI,
        SPONSOR_FARK_KAZANCI,
        SPONSOR_PRIMI,
        BAYI_AKTIFLIK_PRIMI,
        LIDER_DESTEK_PRIMI,
        LIDER_CIKARMA_PRIMI,
        REKLAM_PRIMI,
        ARAC_PRIMI,
        DIGER
    }
}
