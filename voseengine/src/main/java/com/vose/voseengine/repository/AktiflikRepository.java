package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Aktiflik;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AktiflikRepository extends CrudRepository<Aktiflik, Long> {
    Aktiflik findByBayiIdAndAy(Long bayiId, String ay);
}
