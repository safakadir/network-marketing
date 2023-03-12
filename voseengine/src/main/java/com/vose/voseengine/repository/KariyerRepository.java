package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Kariyer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KariyerRepository extends CrudRepository<Kariyer, Long> {
    List<Kariyer> findAllByOrderBySiraNoAsc();

    @Modifying
    @Query("UPDATE Kariyer SET siraNo = (siraNo + ?2) WHERE siraNo > ?1")
    void shiftSiraNoAfter(int siraNo, int shiftBy);

    Kariyer findBySiraNo(int siraNo);
}
