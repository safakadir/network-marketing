package com.vose.voseengine.service;

import com.vose.voseengine.model.entity.Kazanc;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.repository.KazancRepository;
import com.vose.voseengine.service.helper.PageResultConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
public class KazancService {
    @Autowired
    private KazancRepository repository;
    @Autowired
    private BayiService bayiService;

    public Kazanc findOneById(Long id) {
        Optional<Kazanc> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kazanc "+id+" not found");
        return resultOptional.get();
    }

    public PageResult<Kazanc> findPaginated(int page, int perPage, long start, long end) {
        Pageable pageRequest = PageRequest.of(page-1, perPage, Sort.by(Sort.Order.desc("tarih")));
        PageResultConverter converter = new PageResultConverter();
        if(start > 0 && end > 0) {
            return converter.convert(repository.findByTarihBetween(new Date(start), new Date(end), pageRequest));
        } else if(start > 0) {
            return converter.convert(repository.findByTarihGreaterThanEqual(new Date(start), pageRequest));
        } else if(end > 0) {
            return converter.convert(repository.findByTarihLessThanEqual(new Date(end), pageRequest));
        } else {
            return converter.convert(repository.findAll(pageRequest));
        }
    }

    public PageResult<Kazanc> findPaginatedByBayiId(int page, int perPage, Long bayiId, long start, long end) {
        Pageable pageRequest = PageRequest.of(page-1, perPage, Sort.by(Sort.Order.desc("tarih")));
        PageResultConverter converter = new PageResultConverter();
        if(start > 0 && end > 0) {
            return converter.convert(repository.findByBayi_IdAndTarihBetween(bayiId, new Date(start), new Date(end), pageRequest));
        } else if(start > 0) {
            return converter.convert(repository.findByBayi_IdAndTarihGreaterThanEqual(bayiId, new Date(start), pageRequest));
        } else if(end > 0) {
            return converter.convert(repository.findByBayi_IdAndTarihLessThanEqual(bayiId, new Date(end), pageRequest));
        } else {
            return converter.convert(repository.findByBayi_Id(bayiId, pageRequest));
        }
    }

    @Transactional
    public Kazanc addEntity(Kazanc entity) {
        entity.setBayi(bayiService.findSubBayiById(entity.getBayiId()));
        return repository.save(entity);
    }
}
