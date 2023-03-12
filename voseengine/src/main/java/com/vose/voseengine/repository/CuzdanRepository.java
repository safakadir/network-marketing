package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Cuzdan;
import com.vose.voseengine.model.entity.OdemeTalep;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CuzdanRepository extends CrudRepository<Cuzdan, Long> {
    Cuzdan findTopByBayiIdOrderByIdDesc(Long bayiId);
    List<Cuzdan> findAllByOrderByIdDesc();

    List<Cuzdan> findByBayiIdOrderByIdDesc(Long bayiId);
    List<Cuzdan> findByBayiIdAndTarihGreaterThanEqualOrderByIdDesc(Long bayiId, Date start);
    List<Cuzdan> findByBayiIdAndTarihLessThanEqualOrderByIdDesc(Long bayiId, Date end);
    List<Cuzdan> findByBayiIdAndTarihBetweenOrderByIdDesc(Long bayiId, Date start, Date end);

}
