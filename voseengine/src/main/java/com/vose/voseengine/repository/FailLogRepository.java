package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.FailLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailLogRepository extends CrudRepository<FailLog, Long> {
}
