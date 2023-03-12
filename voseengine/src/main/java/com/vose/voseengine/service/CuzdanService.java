package com.vose.voseengine.service;

import com.vose.voseengine.Util;
import com.vose.voseengine.model.entity.Cuzdan;
import com.vose.voseengine.repository.CuzdanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CuzdanService {
    @Autowired
    private CuzdanRepository repository;

    public List<Cuzdan> findAll() {
        return repository.findAllByOrderByIdDesc();
    }

    public Cuzdan findOneById(Long id) {
        Optional<Cuzdan> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuzdan "+id+" not found");
        return resultOptional.get();
    }

    public List<Cuzdan> query(Long bayiId, long start, long end) {
        if(start > 0 && end > 0) {
            return repository.findByBayiIdAndTarihBetweenOrderByIdDesc(bayiId, new Date(start), new Date(end));
        } else if(start > 0) {
            return repository.findByBayiIdAndTarihGreaterThanEqualOrderByIdDesc(bayiId, new Date(start));
        } else if(end > 0) {
            return repository.findByBayiIdAndTarihLessThanEqualOrderByIdDesc(bayiId, new Date(end));
        } else {
            return repository.findByBayiIdOrderByIdDesc(bayiId);
        }
    }

    public Cuzdan findLatestOne(Long bayiId) {
        return repository.findTopByBayiIdOrderByIdDesc(bayiId);
    }

    public Cuzdan withdraw(Long bayiId, double amount, String comment) {
        Cuzdan latest = findLatestOne(bayiId);
        double prevToplam = latest != null ? latest.getCuzdanToplam() : 0;
        if(BigDecimal.valueOf(prevToplam).compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount is less than total amount");
        }
        Cuzdan cuzdan = new Cuzdan();
        cuzdan.setBayiId(bayiId);
        cuzdan.setMiktar(amount);
        cuzdan.setYon(-1);
        cuzdan.setCuzdanToplam(Util.round(prevToplam-amount, 2));
        cuzdan.setAciklama(comment);
        return repository.save(cuzdan);
    }
}
