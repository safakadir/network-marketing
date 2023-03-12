package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.AppSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppSettingRepository extends CrudRepository<AppSetting, String> {
    List<AppSetting> findByNameStartsWith(String prefix);
}
