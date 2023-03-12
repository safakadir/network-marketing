package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Siparis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SiparisRepository extends PagingAndSortingRepository<Siparis, Long> {
    @Query("SELECT s FROM Siparis s WHERE id = ?2 AND bayi.id = ?1")
    Siparis findByBayiIdAndId(Long bayiId, Long id);

    Page<Siparis> findBySiparisTarihiBetween(Date start, Date end, Pageable pageable);
    Page<Siparis> findBySiparisTarihiGreaterThanEqual(Date start, Pageable pageable);
    Page<Siparis> findBySiparisTarihiLessThanEqual(Date end, Pageable pageable);
    //findAll defined in parent

    @Query("SELECT s FROM Siparis s WHERE s.bayi.id = ?1 AND s.siparisTarihi >= ?2 AND s.siparisTarihi <= ?3")
    Page<Siparis> findByBayiIdAndSiparisTarihiBetween(Long bayiId, Date start, Date end, Pageable pageable);
    @Query("SELECT s FROM Siparis s WHERE s.bayi.id = ?1 AND s.siparisTarihi >= ?2")
    Page<Siparis> findByBayiIdAndSiparisTarihiGreaterThanEqual(Long bayiId, Date start, Pageable pageable);
    @Query("SELECT s FROM Siparis s WHERE s.bayi.id = ?1 AND s.siparisTarihi <= ?2")
    Page<Siparis> findByBayiIdAndSiparisTarihiLessThanEqual(Long bayiId, Date end, Pageable pageable);
    @Query("SELECT s FROM Siparis s WHERE s.bayi.id = ?1")
    Page<Siparis> findByBayiId(Long bayiId, Pageable pageable);

    Page<Siparis> findBySiparisDurumAndSiparisTarihiBetween(Siparis.Durum durum, Date start, Date end, Pageable pageable);
    Page<Siparis> findBySiparisDurumAndSiparisTarihiGreaterThanEqual(Siparis.Durum durum, Date start, Pageable pageable);
    Page<Siparis> findBySiparisDurumAndSiparisTarihiLessThanEqual(Siparis.Durum durum, Date end, Pageable pageable);
    Page<Siparis> findBySiparisDurum(Siparis.Durum durum, Pageable pageable);

    @Query("SELECT SUM(urunSayisi) FROM Siparis WHERE bayi.id = ?1 AND odemeOnayTarih >= ?2 AND odemeOnay = 1 GROUP BY bayi.id")
    Integer buAyUrunSayisi(Long bayiId, Date monthStart);

    @Query("SELECT SUM(s.toplamCv) FROM Siparis s JOIN s.bayi b WHERE b.sponsorId = ?1 AND s.odemeOnayTarih >= ?2 AND s.odemeOnay = 1 GROUP BY b.sponsorId")
    Double direkBayiBuAyPv(Long sponsorId, Date monthStart);

}
