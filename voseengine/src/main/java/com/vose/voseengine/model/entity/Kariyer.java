package com.vose.voseengine.model.entity;

import javax.persistence.*;

@Entity
@Table(name="ar_kariyerler")
public class Kariyer implements Identable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kariyerAdi;
    private Integer siraNo;
    private boolean baslangicPaket;
    private Integer baslangicPaketTutar;
    private Integer kariyerPuan;
    private Integer aktiflikSarti;
    private Integer alisverisIndirimi;
    private Integer binaryEslesme;
    private String farkKazanci;
    private String sponsorPrimi;
    private String ekipAlisveris;
    private String ekipKazanc;
    private Integer bayiAktiflikPuan;
    private Integer bayiAktiflikKazanc;
    private int liderDestek;
    private int liderCikarma;
    private int tazminat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKariyerAdi() {
        return kariyerAdi;
    }

    public void setKariyerAdi(String kariyerAdi) {
        this.kariyerAdi = kariyerAdi;
    }

    public Integer getSiraNo() {
        return siraNo;
    }

    public void setSiraNo(Integer siraNo) {
        this.siraNo = siraNo;
    }

    public boolean getBaslangicPaket() {
        return baslangicPaket;
    }

    public void setBaslangicPaket(boolean baslangicPaket) {
        this.baslangicPaket = baslangicPaket;
    }

    public Integer getBaslangicPaketTutar() {
        return baslangicPaketTutar;
    }

    public void setBaslangicPaketTutar(Integer baslangicPaketTutar) {
        this.baslangicPaketTutar = baslangicPaketTutar;
    }

    public Integer getKariyerPuan() {
        return kariyerPuan;
    }

    public void setKariyerPuan(Integer kariyerPuan) {
        this.kariyerPuan = kariyerPuan;
    }

    public Integer getAktiflikSarti() {
        return aktiflikSarti;
    }

    public void setAktiflikSarti(Integer aktiflikSarti) {
        this.aktiflikSarti = aktiflikSarti;
    }

    public Integer getAlisverisIndirimi() {
        return alisverisIndirimi;
    }

    public void setAlisverisIndirimi(Integer alisverisIndirimi) {
        this.alisverisIndirimi = alisverisIndirimi;
    }

    public Integer getBinaryEslesme() {
        return binaryEslesme;
    }

    public void setBinaryEslesme(Integer binaryEslesme) {
        this.binaryEslesme = binaryEslesme;
    }

    public String getFarkKazanci() {
        return farkKazanci;
    }

    public void setFarkKazanci(String farkKazanci) {
        this.farkKazanci = farkKazanci;
    }

    public String getSponsorPrimi() {
        return sponsorPrimi;
    }

    public void setSponsorPrimi(String sponsorPrimi) {
        this.sponsorPrimi = sponsorPrimi;
    }

    public String getEkipAlisveris() {
        return ekipAlisveris;
    }

    public void setEkipAlisveris(String ekipAlisveris) {
        this.ekipAlisveris = ekipAlisveris;
    }

    public String getEkipKazanc() {
        return ekipKazanc;
    }

    public void setEkipKazanc(String ekipKazanc) {
        this.ekipKazanc = ekipKazanc;
    }

    public Integer getBayiAktiflikPuan() {
        return bayiAktiflikPuan;
    }

    public void setBayiAktiflikPuan(Integer bayiAktiflikPuan) {
        this.bayiAktiflikPuan = bayiAktiflikPuan;
    }

    public Integer getBayiAktiflikKazanc() {
        return bayiAktiflikKazanc;
    }

    public void setBayiAktiflikKazanc(Integer bayiAktiflikKazanc) {
        this.bayiAktiflikKazanc = bayiAktiflikKazanc;
    }

    public Integer getLiderDestek() {
        return liderDestek;
    }

    public void setLiderDestek(Integer liderDestek) {
        this.liderDestek = liderDestek;
    }

    public Integer getLiderCikarma() {
        return liderCikarma;
    }

    public void setLiderCikarma(Integer liderCikarma) {
        this.liderCikarma = liderCikarma;
    }

    public Integer getTazminat() {
        return tazminat;
    }

    public void setTazminat(Integer tazminat) {
        this.tazminat = tazminat;
    }

    @Override
    public String toString() {
        return "Kariyer{" +
                "id=" + id +
                ", kariyerAdi='" + kariyerAdi + '\'' +
                ", siraNo=" + siraNo +
                ", baslangicPaket=" + baslangicPaket +
                ", baslangicPaketTutar=" + baslangicPaketTutar +
                ", kariyerPuan=" + kariyerPuan +
                '}';
    }
}
