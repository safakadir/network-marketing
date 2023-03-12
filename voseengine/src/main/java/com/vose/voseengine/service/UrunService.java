package com.vose.voseengine.service;

import com.vose.voseengine.Util;
import com.vose.voseengine.model.entity.Urun;
import com.vose.voseengine.model.entity.UrunStok;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.service.helper.PageResultConverter;
import com.vose.voseengine.repository.UrunRepository;
import com.vose.voseengine.repository.UrunStokRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UrunService extends CrudService<Urun> {
    @Autowired
    private UrunRepository repository;
    @Autowired
    private UrunStokRepository stokRepository;

    public PageResult<Urun> findPaginated(int page, int perPage, String sort, String search, Long kategoriId, boolean onecikan) {
        Sort sortObj;
        if(sort.equals("id")) sortObj = Sort.by(Sort.Order.desc(sort));
        else sortObj = Sort.by(Sort.Order.asc(sort));
        Pageable pageRequest = PageRequest.of(page-1, perPage, sortObj);
        PageResultConverter converter = new PageResultConverter();
        if(onecikan) {
            return converter.convert(repository.findByStarredTrue(pageRequest));
        }
        else if(Util.isNullOrEmpty(search)) {
            if(kategoriId > 0) {
                return converter.convert(repository.findByKategoriId(kategoriId, pageRequest));
            }
            else {
                return converter.convert(repository.findAll(pageRequest));
            }
        }
        else {
            if(kategoriId > 0) {
                return converter.convert(repository.findByKategoriIdAndUrunAdiLikeIgnoreCase(kategoriId, '%' + search + '%', pageRequest));
            }
            else {
                return converter.convert(repository.findByUrunAdiLikeIgnoreCase('%'+search+'%', pageRequest));
            }
        }
    }

    @Override
    public List<Urun> findAll() {
        return null;
    }

    @Override
    public Urun findOneById(Long id) {
        Optional<Urun> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Siparis "+id+" not found");
        return resultOptional.get();
    }

    public List<Urun> findManyByIds(List<Long> ids) {
        return repository.findByIdIn(ids);
    }

    @Transactional
    @Override
    public Urun addEntity(Urun entity) {
        //TODO image, kdv, etc.
        UrunStok urunStok = entity.getUrunStok();
        entity.setUrunStok(null);
        entity = repository.save(entity);
        urunStok.setUrunId(entity.getId());
        urunStok = stokRepository.save(urunStok);
        entity.setUrunStok(urunStok);
        return repository.save(entity);
    }

    @Transactional
    @Override
    public Urun updateEntity(Urun entity) {
        Urun existing = getExisting(entity.getId(), repository);
        return repository.save(entity);
    }

    @Override
    public boolean deleteEntity(Long id) {
        Urun existing = getExisting(id, repository);
        repository.deleteById(id);
        return true;
    }

    public UrunStok getUrunStokById(Long urunId) {
        return stokRepository.findByUrunId(urunId);
    }
}
