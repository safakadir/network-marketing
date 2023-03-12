package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Kategori;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KategoriRepository extends CrudRepository<Kategori, Long> {
    List<Kategori> findAllByOrderByKategoriAdi();
}
