package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Kazanc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KazancRepository extends PagingAndSortingRepository<Kazanc, Long> {

    Page<Kazanc> findAll(Pageable pageable);
    Page<Kazanc> findByTarihGreaterThanEqual(Date start, Pageable pageable);
    Page<Kazanc> findByTarihLessThanEqual(Date end, Pageable pageable);
    Page<Kazanc> findByTarihBetween(Date start, Date end, Pageable pageable);

    Page<Kazanc> findByBayi_Id(Long bayiId, Pageable pageable);
    Page<Kazanc> findByBayi_IdAndTarihGreaterThanEqual(Long bayiId, Date start, Pageable pageable);
    Page<Kazanc> findByBayi_IdAndTarihLessThanEqual(Long bayiId, Date end, Pageable pageable);
    Page<Kazanc> findByBayi_IdAndTarihBetween(Long bayiId, Date start, Date end, Pageable pageable);

    @Modifying
    @Query("UPDATE Kazanc SET silindi=1 WHERE bayi.id = ?1 AND tarih >= ?2 AND cuzdanaIslendi = 0")
    void kazancSifirla(Long bayiId, Date startDate);

    @Query("SELECT k FROM Kazanc k WHERE k.bayi.id = ?1 AND k.tarih >= ?2 AND k.silindi = 0 AND k.cuzdanaIslendi = 0")
    List<Kazanc> getAylikKazanc(Long bayiId, Date startDate);
}
