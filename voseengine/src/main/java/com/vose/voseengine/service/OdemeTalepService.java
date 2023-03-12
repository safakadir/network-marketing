package com.vose.voseengine.service;

import com.vose.voseengine.model.entity.OdemeTalep;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.repository.OdemeTalepRepository;
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
import java.util.List;
import java.util.Optional;

@Service
public class OdemeTalepService extends CrudService<OdemeTalep> {
    @Autowired
    private OdemeTalepRepository repository;
    @Autowired
    private CuzdanService cuzdanService;

    public List<OdemeTalep> findAll() {
        throw new RuntimeException("Not implemented");
    }

    public OdemeTalep findOneById(Long id) {
        Optional<OdemeTalep> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "OdemeTalep "+id+" not found");
        return resultOptional.get();
    }

    public OdemeTalep findOneById(Long bayiId, Long id) {
        return repository.findByBayiIdAndId(bayiId, id);
    }

    public PageResult<OdemeTalep> queryPaginated(OdemeTalep.Durum durum, int page, int perPage) {
        Pageable pageRequest = PageRequest.of(page-1, perPage, Sort.by(Sort.Order.desc("id")));
        PageResultConverter converter = new PageResultConverter();
        if(durum == OdemeTalep.Durum.NONE) {
            return converter.convert(repository.findAll(pageRequest));
        }
        else {
            return converter.convert(repository.findByDurum(durum, pageRequest));
        }
    }

    public List<OdemeTalep> query(Long bayiId, OdemeTalep.Durum durum, long start, long end) {
        if(durum == OdemeTalep.Durum.NONE) {
            if(start > 0 && end > 0) {
                return repository.findByBayiIdAndTalepTarihBetweenOrderByIdDesc(bayiId, new Date(start), new Date(end));
            } else if(start > 0) {
                return repository.findByBayiIdAndTalepTarihGreaterThanEqualOrderByIdDesc(bayiId, new Date(start));
            } else if(end > 0) {
                return repository.findByBayiIdAndTalepTarihLessThanEqualOrderByIdDesc(bayiId, new Date(end));
            } else {
                return repository.findByBayiIdOrderByIdDesc(bayiId);
            }
        }
        else {
            if(start > 0 && end > 0) {
                return repository.findByBayiIdAndDurumAndTalepTarihBetweenOrderByIdDesc(bayiId, durum, new Date(start), new Date(end));
            } else if(start > 0) {
                return repository.findByBayiIdAndDurumAndTalepTarihGreaterThanEqualOrderByIdDesc(bayiId, durum, new Date(start));
            } else if(end > 0) {
                return repository.findByBayiIdAndDurumAndTalepTarihLessThanEqualOrderByIdDesc(bayiId, durum, new Date(end));
            } else {
                return repository.findByBayiIdAndDurumOrderByIdDesc(bayiId, durum);
            }
        }
    }

    @Transactional
    public OdemeTalep changeStatus(Long id, OdemeTalep.Durum durum) {
        OdemeTalep current = findOneById(id);
        if(durum == OdemeTalep.Durum.ODENDI) {
            if(current.getDurum() == OdemeTalep.Durum.ODENDI) {
                return current;
            }
            else if(current.getDurum() == OdemeTalep.Durum.IPTAL_EDILDI) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only BEKLIYOR can be approved!");
            }
            else {
                cuzdanService.withdraw(current.getBayiId(), current.getMiktar(), "Onaylanan ödeme talebiniz: #"+current.getId());
                current.setDurum(OdemeTalep.Durum.ODENDI);
                return repository.save(current);
            }
        }
        else if(durum == OdemeTalep.Durum.IPTAL_EDILDI) {
            if(current.getDurum() == OdemeTalep.Durum.IPTAL_EDILDI) {
                return current;
            }
            else if(current.getDurum() == OdemeTalep.Durum.ODENDI) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only BEKLIYOR can be cancelled!");
            }
            else {
                current.setDurum(OdemeTalep.Durum.IPTAL_EDILDI);
                return repository.save(current);
            }
        }
        return null;
    }

    @Transactional
    @Override
    public OdemeTalep addEntity(OdemeTalep entity) {
        entity.setDurum(OdemeTalep.Durum.BEKLIYOR);
        return repository.save(entity);
    }

    @Transactional
    @Override
    public OdemeTalep updateEntity(OdemeTalep entity) {
        OdemeTalep existing = getExisting(entity.getId(), repository);
        return repository.save(entity);
    }

    @Override
    public boolean deleteEntity(Long id) {
        OdemeTalep existing = getExisting(id, repository);
        // ???
        repository.deleteById(id);
        return true;
    }

    public boolean deleteEntity(Long bayiId, Long id) {
        OdemeTalep existing = findOneById(bayiId, id);
        if(existing == null) return false;
        repository.deleteById(id);
        return true;
    }
}
