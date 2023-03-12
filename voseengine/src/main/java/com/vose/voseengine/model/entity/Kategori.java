package com.vose.voseengine.model.entity;

import javax.persistence.*;

@Entity
@Table(name="ar_kategoriler")
public class Kategori implements Identable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kategoriAdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    @Override
    public String toString() {
        return "Kategori{" +
                "id=" + id +
                ", kategoriAdi='" + kategoriAdi + '\'' +
                '}';
    }
}
