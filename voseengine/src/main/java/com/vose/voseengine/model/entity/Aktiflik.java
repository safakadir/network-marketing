package com.vose.voseengine.model.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ar_aktiflik")
public class Aktiflik implements Identable<Long>, BayiRelatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bayiId;
    private String ay;
    private int urunSayisi;
    @UpdateTimestamp
    private Date guncelleme;
    @Transient
    private boolean durum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getBayiId() {
        return bayiId;
    }

    public void setBayiId(Long bayiId) {
        this.bayiId = bayiId;
    }

    public String getAy() {
        return ay;
    }

    public void setAy(String ay) {
        this.ay = ay;
    }

    public int getUrunSayisi() {
        return urunSayisi;
    }

    public void setUrunSayisi(int urunSayisi) {
        this.urunSayisi = urunSayisi;
    }

    public Date getGuncelleme() {
        return guncelleme;
    }

    public void setGuncelleme(Date guncelleme) {
        this.guncelleme = guncelleme;
    }

    public boolean getDurum() {
        return durum;
    }

    public void setDurum(boolean durum) {
        this.durum = durum;
    }

    @Override
    public String toString() {
        return "Aktiflik{" +
                "bayiId=" + bayiId +
                ", ay='" + ay + '\'' +
                '}';
    }
}
