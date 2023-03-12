package com.vose.voseengine.model.entity;

import javax.persistence.*;

@Entity
@Table(name="ar_siparis_detay")
public class SiparisDetay implements Identable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private	Long id;
    private	Long siparisId;
    //@ManyToOne
    //private	Urun urun;
    // urun silmeye izin verdiremediğimiz için db tarafında foreign key'i kaldırdık
    // bu sefer de nedenini anlayamadığımız bir şekilde siparişleri return ederken hibernate hata vermeye başladı.
    // debugda herşeyi çekip return edeceği şeyi hazır ediyordu ama return ederken hibernate hata veriyordu.
    // muhtemelen cleanup(hatada yazan şey buydu) yapıyor, return edeceği şey de ona kurban gidiyor return edemiyor vs gibi bir hikaye oluyor emin değilim
    // ama buna çevirince çalıştı. diğer yerlerde de farklı geliştirmelerle buna uydurduk
    private Long urunId;
    private	Integer miktar;
    private	Double katalogBirimFiyat;
    private	Double indirimliBirimFiyat;
    private	Double tutar;
    private Double birimPv;
    private Double birimCv;
    private Double toplamPv;
    private Double toplamCv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(Long siparisId) {
        this.siparisId = siparisId;
    }

    /*public Urun getUrun() {
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }*/

    public Long getUrunId() {
        return urunId;
    }

    public void setUrunId(Long urunId) {
        this.urunId = urunId;
    }

    public Integer getMiktar() {
        return miktar;
    }

    public void setMiktar(Integer miktar) {
        this.miktar = miktar;
    }

    public Double getKatalogBirimFiyat() {
        return katalogBirimFiyat;
    }

    public void setKatalogBirimFiyat(Double katalogBirimFiyat) {
        this.katalogBirimFiyat = katalogBirimFiyat;
    }

    public Double getIndirimliBirimFiyat() {
        return indirimliBirimFiyat;
    }

    public void setIndirimliBirimFiyat(Double indirimliBirimFiyat) {
        this.indirimliBirimFiyat = indirimliBirimFiyat;
    }

    public Double getTutar() {
        return tutar;
    }

    public void setTutar(Double tutar) {
        this.tutar = tutar;
    }

    public Double getBirimPv() {
        return birimPv;
    }

    public void setBirimPv(Double birimPv) {
        this.birimPv = birimPv;
    }

    public Double getBirimCv() {
        return birimCv;
    }

    public void setBirimCv(Double birimCv) {
        this.birimCv = birimCv;
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

    @Override
    public String toString() {
        return "SiparisDetay{" +
                "id=" + id +
                ", siparisId=" + siparisId +
                //", urunId=" + urun.getId() +
                ", urunId=" + urunId +
                ", miktar=" + miktar +
                ", indirimliBirimFiyat=" + indirimliBirimFiyat +
                ", tutar=" + tutar +
                '}';
    }
}
