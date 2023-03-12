package com.vose.voseengine.service;

import com.vose.voseengine.model.entity.Kategori;
import com.vose.voseengine.repository.KategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class KategoriService extends CrudService<Kategori> {
    @Autowired
    private KategoriRepository repository;

    @Override
    public List<Kategori> findAll() {
        return repository.findAllByOrderByKategoriAdi();
    }

    @Override
    public Kategori findOneById(Long id) {
        Optional<Kategori> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori "+id+" not found");
        return resultOptional.get();
    }

    @Transactional
    @Override
    public Kategori addEntity(Kategori entity) {
        return repository.save(entity);
    }

    @Transactional
    @Override
    public Kategori updateEntity(Kategori entity) {
        Kategori existing = getExisting(entity.getId(), repository);
        return repository.save(entity);
    }

    @Override
    public boolean deleteEntity(Long id) {
        Kategori existing = getExisting(id, repository);
        // ???
        repository.deleteById(id);
        return true;
    }
}
