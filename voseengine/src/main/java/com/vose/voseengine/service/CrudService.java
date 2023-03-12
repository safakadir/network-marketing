package com.vose.voseengine.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public abstract class CrudService<T> {

    public abstract List<T> findAll();
    public abstract T findOneById(Long id);
    public abstract T addEntity(T entity);
    public abstract T updateEntity(T entity);
    public abstract boolean deleteEntity(Long id);

    protected T getExisting(Long id, CrudRepository<T, Long> repository) {
        Optional<T> existingOptional = repository.findById(id);
        if(!existingOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existing entity with provided id:"+id+" in "+this.getClass().getSimpleName());
        }
        return existingOptional.get();
    }
}
