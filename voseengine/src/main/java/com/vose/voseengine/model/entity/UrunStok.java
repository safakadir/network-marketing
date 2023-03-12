package com.vose.voseengine.model.entity;

import javax.persistence.*;

@Entity
@Table(name="ar_urun_stok")
public class UrunStok implements Identable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long urunId;
    private Integer miktar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "UrunStok{" +
                "id=" + id +
                ", urunId=" + urunId +
                ", miktar=" + miktar +
                '}';
    }
}
