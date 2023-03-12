package com.vose.voseengine.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vose.voseengine.model.converter.AdresConverter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ar_siparisler")
public class Siparis implements Identable<Long>, BayiRelatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private SubBayi bayi;
    @CreationTimestamp
    private Date siparisTarihi;
    private Integer urunSayisi;
    @OneToMany(mappedBy = "siparisId")
    private List<SiparisDetay> siparisItems;
    private String odemeYontemi;
    private Integer indirim;
    private Double urunTutar;
    private Double odenenTutar;
    private Double toplamPv;
    private Double toplamCv;
    private String siparisVeren;
    @Convert(converter = AdresConverter.class)
    private Adres adres;
    private boolean paketAlisveris;
    private boolean odemeOnay;
    private Date odemeOnayTarih;
    private String odemeOnaylayan;
    @Enumerated(EnumType.STRING)
    private Durum siparisDurum;
    @UpdateTimestamp
    private Date sonGuncelleme;
    private String sonGuncelleyen;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubBayi getBayi() {
        return bayi;
    }

    @JsonIgnore
    @Override
    public Long getBayiId() { return bayi != null ? bayi.getId() : null; }

    public void setBayi(SubBayi bayi) {
        this.bayi = bayi;
    }

    public Date getSiparisTarihi() {
        return siparisTarihi;
    }

    public void setSiparisTarihi(Date siparisTarihi) {
        this.siparisTarihi = siparisTarihi;
    }

    public Integer getUrunSayisi() {
        return urunSayisi;
    }

    public void setUrunSayisi(Integer urunSayisi) {
        this.urunSayisi = urunSayisi;
    }

    public List<SiparisDetay> getSiparisItems() {
        return siparisItems;
    }

    public void setSiparisItems(List<SiparisDetay> siparisItems) {
        this.siparisItems = siparisItems;
    }

    public String getOdemeYontemi() {
        return odemeYontemi;
    }

    public void setOdemeYontemi(String odemeYontemi) {
        this.odemeYontemi = odemeYontemi;
    }

    public Integer getIndirim() {
        return indirim;
    }

    public void setIndirim(Integer indirim) {
        this.indirim = indirim;
    }

    public Double getUrunTutar() {
        return urunTutar;
    }

    public void setUrunTutar(Double urunTutar) {
        this.urunTutar = urunTutar;
    }

    public Double getOdenenTutar() {
        return odenenTutar;
    }

    public void setOdenenTutar(Double odenenTutar) {
        this.odenenTutar = odenenTutar;
    }

    public Double getToplamPv() {
        return toplamPv;
    }

    public void setToplamPv(Double toplamPv) {
        this.toplamPv = toplamPv;
    }

    public Double getToplamCv() {
        return toplamCv;
    }

    public void setToplamCv(Double toplamCv) {
        this.toplamCv = toplamCv;
    }

    public String getSiparisVeren() {
        return siparisVeren;
    }

    public void setSiparisVeren(String siparisVeren) {
        this.siparisVeren = siparisVeren;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public boolean isPaketAlisveris() {
        return paketAlisveris;
    }

    public void setPaketAlisveris(boolean paketAlisveris) {
        this.paketAlisveris = paketAlisveris;
    }

    public boolean isOdemeOnay() {
        return odemeOnay;
    }

    public void setOdemeOnay(boolean odemeOnay) {
        this.odemeOnay = odemeOnay;
    }

    public Date getOdemeOnayTarih() {
        return odemeOnayTarih;
    }

    public void setOdemeOnayTarih(Date odemeOnayTarih) {
        this.odemeOnayTarih = odemeOnayTarih;
    }

    public String getOdemeOnaylayan() {
        return odemeOnaylayan;
    }

    public void setOdemeOnaylayan(String odemeOnaylayan) {
        this.odemeOnaylayan = odemeOnaylayan;
    }

    public Durum getSiparisDurum() {
        return siparisDurum;
    }

    public void setSiparisDurum(Durum siparisDurum) {
        this.siparisDurum = siparisDurum;
    }

    public Date getSonGuncelleme() {
        return sonGuncelleme;
    }

    public void setSonGuncelleme(Date sonGuncelleme) {
        this.sonGuncelleme = sonGuncelleme;
    }

    public String getSonGuncelleyen() {
        return sonGuncelleyen;
    }

    public void setSonGuncelleyen(String sonGuncelleyen) {
        this.sonGuncelleyen = sonGuncelleyen;
    }

    @Override
    public String toString() {
        return "Siparis{" +
                "id=" + id +
                ", bayi=" + bayi +
                ", siparisTarihi=" + siparisTarihi +
                ", urunSayisi=" + urunSayisi +
                ", toplamTutar=" + urunTutar +
                ", siparisDurum='" + siparisDurum + '\'' +
                '}';
    }

    public enum Durum {
        NONE,
        ODEME_BEKLENIYOR,
        ODEME_ONAYLANDI,
        HAZIRLANDI,
        KARGOYA_VERILDI,
        TAMAMLANDI
    }
}
