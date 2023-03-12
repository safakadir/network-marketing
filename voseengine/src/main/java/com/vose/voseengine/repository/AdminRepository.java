package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Admin;
import com.vose.voseengine.model.entity.Bayi;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {

    Admin findByIdAndSifre(Long adminId, String sifre);
    Admin findByEmailAndSifre(String email, String sifre);

    @Modifying
    @Query("UPDATE Admin a SET a.sifre=?2 WHERE a.id=?1")
    void changePassword(Long adminId, String newPassword);
}
