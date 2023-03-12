package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.OdemeTalep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OdemeTalepRepository extends PagingAndSortingRepository<OdemeTalep, Long> {

    OdemeTalep findByBayiIdAndId(Long bayiId, Long id);

    Page<OdemeTalep> findAll(Pageable pageable);
    Page<OdemeTalep> findByDurum(OdemeTalep.Durum durum, Pageable pageable);

    List<OdemeTalep> findByBayiIdOrderByIdDesc(Long bayiId);
    List<OdemeTalep> findByBayiIdAndTalepTarihGreaterThanEqualOrderByIdDesc(Long bayiId, Date start);
    List<OdemeTalep> findByBayiIdAndTalepTarihLessThanEqualOrderByIdDesc(Long bayiId, Date end);
    List<OdemeTalep> findByBayiIdAndTalepTarihBetweenOrderByIdDesc(Long bayiId, Date start, Date end);

    List<OdemeTalep> findByBayiIdAndDurumOrderByIdDesc(Long bayiId, OdemeTalep.Durum durum);
    List<OdemeTalep> findByBayiIdAndDurumAndTalepTarihGreaterThanEqualOrderByIdDesc(Long bayiId, OdemeTalep.Durum durum, Date start);
    List<OdemeTalep> findByBayiIdAndDurumAndTalepTarihLessThanEqualOrderByIdDesc(Long bayiId, OdemeTalep.Durum durum, Date end);
    List<OdemeTalep> findByBayiIdAndDurumAndTalepTarihBetweenOrderByIdDesc(Long bayiId, OdemeTalep.Durum durum, Date start, Date end);
}
