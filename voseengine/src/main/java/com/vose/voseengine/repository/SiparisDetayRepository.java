package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.SiparisDetay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiparisDetayRepository extends CrudRepository<SiparisDetay, Long> {
}
