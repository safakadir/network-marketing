package com.vose.voseengine.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ar_odeme_talepler")
public class OdemeTalep implements Identable<Long>, BayiRelatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bayiId;
    private double miktar;
    @CreationTimestamp
    private Date talepTarih;
    @Enumerated(EnumType.STRING)
    private Durum durum;
    @UpdateTimestamp
    private Date durumTarih;
    private String banka;
    private String iban;

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

    public Date getTalepTarih() {
        return talepTarih;
    }

    public void setTalepTarih(Date talepTarih) {
        this.talepTarih = talepTarih;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public Date getDurumTarih() {
        return durumTarih;
    }

    public void setDurumTarih(Date durumTarih) {
        this.durumTarih = durumTarih;
    }

    public String getBanka() {
        return banka;
    }

    public void setBanka(String banka) {
        this.banka = banka;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public enum Durum {
        NONE,
        BEKLIYOR,
        ODENDI,
        IPTAL_EDILDI
    }
}
