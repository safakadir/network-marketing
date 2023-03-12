package com.vose.voseengine.service;

import com.vose.voseengine.model.entity.Adres;
import com.vose.voseengine.repository.AdresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdresService extends CrudService<Adres> {
    @Autowired
    private AdresRepository repository;

    @Override
    public List<Adres> findAll() {
        return (List<Adres>) repository.findAll();
    }

    public List<Adres> findByBayiId(Long bayiId) {
        return repository.findByBayiId(bayiId);
    }

    @Override
    public Adres findOneById(Long id) {
        Optional<Adres> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adres "+id+" not found");
        return resultOptional.get();
    }

    public Adres findOneById(Long bayiId, Long id) {
        return repository.findByBayiIdAndId(bayiId, id);
    }

    @Transactional
    @Override
    public Adres addEntity(Adres entity) {
        return repository.save(entity);
    }

    @Transactional
    @Override
    public Adres updateEntity(Adres entity) {
        Adres existing = getExisting(entity.getId(), repository);
        return repository.save(entity);
    }

    @Override
    public boolean deleteEntity(Long id) {
        Adres existing = getExisting(id, repository);
        // ???
        repository.deleteById(id);
        return true;
    }

    public boolean deleteEntity(Long bayiId, Long id) {
        Adres existing = findOneById(bayiId, id);
        if(existing == null) return false;
        repository.deleteById(id);
        return true;
    }
}
