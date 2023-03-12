package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.BayiPuan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuanRepository extends CrudRepository<BayiPuan, Long> {
    List<BayiPuan> findByBayiId(Long bayiId);
    BayiPuan findByBayiIdAndAltBayiId(Long bayiId, Long altBayiId);

    @Modifying
    @Query("UPDATE BayiPuan bp SET pv=(bp.pv+?3), cv=(bp.cv+?4) WHERE bp.bayiId=?1 AND bp.altBayiId=?2")
    void puanEkle(Long bayiId, Long altBayiId, Double pv, Double cv);

    @Modifying
    @Query("UPDATE BayiPuan bp SET pv=(bp.pv-?3) WHERE bp.bayiId=?1 AND bp.altBayiId=?2")
    void pvDus(Long bayiId, Long altBayiId, Double pv);

    @Modifying
    @Query("UPDATE BayiPuan bp SET cv=(bp.cv-?3) WHERE bp.bayiId=?1 AND bp.altBayiId=?2")
    void cvDus(Long bayiId, Long altBayiId, Double cv);

    @Modifying
    @Query("UPDATE BayiPuan SET pv=0, cv=0 WHERE bayiId=?1")
    void puanSifirla(Long bayiId);
}
