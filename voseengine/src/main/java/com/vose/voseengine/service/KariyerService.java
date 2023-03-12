package com.vose.voseengine.service;

import com.vose.voseengine.model.entity.Kariyer;
import com.vose.voseengine.repository.KariyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KariyerService {
    @Autowired
    private KariyerRepository repository;

    public List<Kariyer> findAll() {
        return repository.findAllByOrderBySiraNoAsc();
    }

    public Kariyer findOneById(Long id) {
        Optional<Kariyer> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kariyer "+id+" not found");
        return resultOptional.get();
    }

    @Transactional
    public Kariyer addKariyer(Kariyer kariyer, int addAfter) {
        repository.shiftSiraNoAfter(addAfter, +1);
        kariyer.setSiraNo(addAfter+1);
        return repository.save(kariyer);
    }

    @Transactional
    public Kariyer updateKariyer(Kariyer kariyer) {
        Kariyer existing = getExisting(kariyer.getId());
        kariyer.setSiraNo(existing.getSiraNo());
        return repository.save(kariyer);
    }

    @Transactional
    public boolean deleteKariyer(Long kariyerId) {
        Kariyer existing = getExisting(kariyerId);
        repository.deleteById(kariyerId);
        repository.shiftSiraNoAfter(existing.getSiraNo(), -1);
        return true;
    }

    @Transactional
    public List<Kariyer> changeOrder(Long kariyerId, int direction) {
        Kariyer existing = getExisting(kariyerId);
        int siraNo = existing.getSiraNo();
        Kariyer neighbour = repository.findBySiraNo(siraNo+direction);
        if(neighbour == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid move operation. Neighbour(siraNo=%d) of self(siraNo=%d) not found.", siraNo+direction, siraNo));
        }
        neighbour.setSiraNo(siraNo);
        existing.setSiraNo(siraNo+direction);
        List<Kariyer> responseList = new ArrayList<>();
        responseList.add(repository.save(neighbour));
        responseList.add(repository.save(existing));
        return responseList;
    }

    private Kariyer getExisting(Long kariyerId) {
        Optional<Kariyer> existingOptional = repository.findById(kariyerId);
        if(!existingOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existing kariyer with provided id:"+kariyerId);
        }
        return existingOptional.get();
    }
}
