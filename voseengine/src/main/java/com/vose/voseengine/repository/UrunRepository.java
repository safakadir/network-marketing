package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Urun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrunRepository extends PagingAndSortingRepository<Urun, Long> {
    Page<Urun> findByStarredTrue(Pageable pageable);
    Page<Urun> findByKategoriId(Long kategoriId, Pageable pageable);
    Page<Urun> findByUrunAdiLikeIgnoreCase(String search, Pageable pageable);
    Page<Urun> findByKategoriIdAndUrunAdiLikeIgnoreCase(Long kategoriId, String search, Pageable pageable);
    List<Urun> findByIdIn(List<Long> ids);
}
