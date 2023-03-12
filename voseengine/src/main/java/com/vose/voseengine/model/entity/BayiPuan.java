package com.vose.voseengine.model.entity;

import com.vose.voseengine.Util;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ar_bayi_puan")
public class BayiPuan implements Identable<Long>, BayiRelatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bayiId;
    private Long altBayiId;
    private double pv;
    private double cv;
    @UpdateTimestamp
    private Date tarih;

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

    public Long getAltBayiId() {
        return altBayiId;
    }

    public void setAltBayiId(Long altBayiId) {
        this.altBayiId = altBayiId;
    }

    public double getPv() {
        return Util.normalizeDouble(pv);
    }

    public void setPv(double pv) {
        this.pv = pv;
    }

    public double getCv() {
        return Util.normalizeDouble(cv);
    }

    public void setCv(double cv) {
        this.cv = cv;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    @Override
    public String toString() {
        return "BayiPuan{" +
                "id=" + id +
                ", bayiId=" + bayiId +
                ", altBayiId=" + altBayiId +
                ", pv=" + pv +
                ", cv=" + cv +
                ", tarih=" + tarih +
                '}';
    }
}
