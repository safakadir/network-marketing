package com.vose.voseengine.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vose.voseengine.Util;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ar_bayiler")
public class Bayi implements Identable<Long>, BayiRelatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tckn;
    private String isim;
    private String soyisim;
    private String dogumTarihi;
    private String email;
    private String telefon1;
    private String telefon2;
    private String ulke;
    private String sehir;
    private String sifre;
    @CreationTimestamp
    private Date tarih;
    private String kaydeden;
    private boolean kayitDurum;
    private Long sponsorId;
    @ManyToOne
    private Kariyer kariyer;
    @OneToMany(mappedBy = "bayiId")
    @BatchSize(size=1)
    @OrderBy("id DESC")
    private List<Aktiflik> aktiflikler;
    private Long aybasiKariyerId;
    private int derinlik=1;
    private int kolSira=1;
    @OneToMany(mappedBy = "bayiId")
    private List<BayiPuan> puanlar;
    @OneToMany(mappedBy = "sponsorId")
    private List<SubBayi> altBayiler;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(String dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon1() {
        return telefon1;
    }

    public void setTelefon1(String telefon1) {
        this.telefon1 = telefon1;
    }

    public String getTelefon2() {
        return telefon2;
    }

    public void setTelefon2(String telefon2) {
        this.telefon2 = telefon2;
    }

    public String getUlke() {
        return ulke;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    @JsonIgnore
    public String getSifre() {
        return sifre;
    }

    @JsonIgnore
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public String getKaydeden() {
        return kaydeden;
    }

    public void setKaydeden(String kaydeden) {
        this.kaydeden = kaydeden;
    }

    public boolean getKayitDurum() {
        return kayitDurum;
    }

    public void setKayitDurum(boolean kayitDurum) {
        this.kayitDurum = kayitDurum;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Kariyer getKariyer() {
        return kariyer;
    }

    public void setKariyer(Kariyer kariyer) {
        this.kariyer = kariyer;
    }

    //Son ayın aktifliğini direk entity'e map'leyemedim. Olayı getter ile çözüyorum.
    public Aktiflik getAktiflik() {
        if(aktiflikler == null || aktiflikler.isEmpty()) return null;
        Aktiflik aktiflik = aktiflikler.get(0);
        if(!aktiflik.getAy().equals(Util.monthKeyTolerated())) return null;
        if(kariyer.getAktiflikSarti().compareTo(aktiflik.getUrunSayisi()) <= 0) aktiflik.setDurum(true);
        return aktiflik;
    }

    public Long getAybasiKariyerId() {
        return aybasiKariyerId;
    }

    public void setAybasiKariyerId(Long aybasiKariyerId) {
        this.aybasiKariyerId = aybasiKariyerId;
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

    public List<BayiPuan> getPuanlar() {
        return puanlar;
    }

    public void setPuanlar(List<BayiPuan> puanlar) {
        this.puanlar = puanlar;
    }

    public List<SubBayi> getAltBayiler() {
        return altBayiler;
    }

    public void setAltBayiler(List<SubBayi> altBayiler) {
        this.altBayiler = altBayiler;
    }

    public String getIsimSoyisim() {
        return isim+" "+soyisim;
    }

    public double getTotalPv() {
        if(puanlar == null || puanlar.size() == 0) return 0;
        return Util.normalizeDouble(
                puanlar.stream().map(BayiPuan::getPv).reduce(0.0, Double::sum)
        );
    }

    public double getTotalCv() {
        if(puanlar == null || puanlar.size() == 0) return 0;
        return Util.normalizeDouble(
                puanlar.stream().map(BayiPuan::getCv).reduce(0.0, Double::sum)
        );
    }

    public String getIdentity() {
        return String.format("%d - %s", id, getIsimSoyisim());
    }

    @JsonIgnore
    @Override
    public Long getBayiId() {
        return id;
    }

    @Override
    public String toString() {
        return "Bayi{" +
                "identity=" + getIdentity() +
                ", sponsorId=" + getSponsorId() +
                ", kariyer=" + kariyer +
                '}';
    }
}
