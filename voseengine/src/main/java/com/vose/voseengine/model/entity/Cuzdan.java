package com.vose.voseengine.model.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ar_cuzdan")
public class Cuzdan implements Identable<Long>, BayiRelatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bayiId;
    private double miktar;
    private int yon;
    private String aciklama;
    private double cuzdanToplam;
    @CreationTimestamp
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

    public double getMiktar() {
        return miktar;
    }

    public void setMiktar(double miktar) {
        this.miktar = miktar;
    }

    public int getYon() {
        return yon;
    }

    public void setYon(int yon) {
        this.yon = yon;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public double getCuzdanToplam() {
        return cuzdanToplam;
    }

    public void setCuzdanToplam(double cuzdanToplam) {
        this.cuzdanToplam = cuzdanToplam;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    @Override
    public String toString() {
        return "Cuzdan{" +
                "id=" + id +
                ", bayiId=" + bayiId +
                ", miktar=" + miktar +
                ", yon=" + yon +
                ", cuzdan_toplam=" + cuzdanToplam +
                '}';
    }
}
