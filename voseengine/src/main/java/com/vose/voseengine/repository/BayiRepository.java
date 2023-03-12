package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.SubBayi;
import com.vose.voseengine.model.entity.Bayi;
import com.vose.voseengine.model.entity.Kariyer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//public interface BayiRepository extends CrudRepository<Bayi, Long>, BayiRepositoryCustom {
@Repository
public interface BayiRepository extends PagingAndSortingRepository<Bayi, Long> {

    Page<Bayi> findAll(Pageable pageable);

    Bayi findBayiById(Long bayiId);
    SubBayi findSubBayiById(Long altBayiId);

    Bayi findByIdAndSifre(Long bayiId, String sifre);

    List<Bayi> findBySponsorIdOrderByKolSiraAsc(Long sponsorId);

    @Query("SELECT k FROM Bayi b, Kariyer k WHERE b.id = ?1 AND k.siraNo = (b.kariyer.siraNo+1)")
    Kariyer getNextKariyer(Long bayiId);

    Bayi findTopBySponsorIdOrderByKolSiraDesc(Long sponsorId);

    @Query("SELECT id FROM Bayi")
    List<Long> findAllBayiIds(Sort sort);

    List<SubBayi> findByIdIn(List<Long> ids);

    @Query("SELECT b FROM SubBayi b WHERE CONCAT(b.id, '') LIKE CONCAT('%', CONCAT(?1, '%'))")
    List<SubBayi> findTop10ByIdLike(String idStr);
    @Query("SELECT b FROM SubBayi b WHERE LOWER(b.isim) LIKE CONCAT('%', CONCAT(LOWER(?1), '%'))")
    List<SubBayi> findTop10ByIsimLike(String isim);
    @Query("SELECT b FROM SubBayi b WHERE LOWER(b.soyisim) LIKE CONCAT('%', CONCAT(LOWER(?1), '%'))")
    List<SubBayi> findTop10BySoyisimLike(String soyisim);

    @Modifying
    @Query("UPDATE Bayi b SET b.sifre=?3 WHERE b.id=?1 AND b.sifre=?2")
    void changePassword(Long bayiId, String oldPassword, String newPassword);

    @Modifying
    @Query("UPDATE Bayi b SET b.sifre=?2 WHERE b.id=?1")
    void changePassword(Long bayiId, String newPassword);
}
