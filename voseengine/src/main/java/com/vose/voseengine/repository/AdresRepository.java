package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Adres;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresRepository extends CrudRepository<Adres, Long> {
    List<Adres> findByBayiId(Long bayiId);
    Adres findByBayiIdAndId(Long bayiId, Long id);
}
