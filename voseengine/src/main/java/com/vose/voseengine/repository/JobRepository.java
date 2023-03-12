package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<Job, Long> {
    Job findTopByProcessedFalseOrderByRootIdAscIdAsc();
}
