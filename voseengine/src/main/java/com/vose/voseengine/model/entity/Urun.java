package com.vose.voseengine.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ar_urunler")
public class Urun implements Identable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Kategori kategori;
    private String urunAdi;
    private String urunKodu;
    @OneToOne
    private UrunStok urunStok;
    private Double netFiyat;
    private Integer kdv;
    private Double satisFiyat;
    private Double pv;
    private Double cv;
    private String img;
    private String aciklama;
    private boolean starred;
    @CreationTimestamp
    private Date eklenmeTarih;
    @UpdateTimestamp
    private Date guncellemeTarih;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getUrunKodu() {
        return urunKodu;
    }

    public void setUrunKodu(String urunKodu) {
        this.urunKodu = urunKodu;
    }

    public UrunStok getUrunStok() {
        return urunStok;
    }

    public void setUrunStok(UrunStok urunStok) {
        this.urunStok = urunStok;
    }

    public Double getNetFiyat() {
        return netFiyat;
    }

    public void setNetFiyat(Double netFiyat) {
        this.netFiyat = netFiyat;
    }

    public Integer getKdv() {
        return kdv;
    }

    public void setKdv(Integer kdv) {
        this.kdv = kdv;
    }

    public Double getSatisFiyat() {
        return satisFiyat;
    }

    public void setSatisFiyat(Double satisFiyat) {
        this.satisFiyat = satisFiyat;
    }

    public Double getPv() {
        return pv;
    }

    public void setPv(Double pv) {
        this.pv = pv;
    }

    public Double getCv() {
        return cv;
    }

    public void setCv(Double cv) {
        this.cv = cv;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public boolean getStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public Date getEklenmeTarih() {
        return eklenmeTarih;
    }

    public void setEklenmeTarih(Date eklenmeTarih) {
        this.eklenmeTarih = eklenmeTarih;
    }

    public Date getGuncellemeTarih() {
        return guncellemeTarih;
    }

    public void setGuncellemeTarih(Date guncellemeTarih) {
        this.guncellemeTarih = guncellemeTarih;
    }

    @Override
    public String toString() {
        return "Urun{" +
                "id=" + id +
                ", urunAdi='" + urunAdi + '\'' +
                ", urunKodu='" + urunKodu + '\'' +
                ", satisFiyat=" + satisFiyat +
                ", pv=" + pv +
                ", cv=" + cv +
                '}';
    }
}
