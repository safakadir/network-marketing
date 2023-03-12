package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.UrunStok;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrunStokRepository extends CrudRepository<UrunStok, Long> {
    UrunStok findByUrunId(Long urunId);
}
