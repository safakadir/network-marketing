package com.vose.voseengine.service;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.model.entity.Siparis;
import com.vose.voseengine.model.entity.SiparisDetay;
import com.vose.voseengine.model.service.MetaSiparis;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.repository.SiparisRepository;
import com.vose.voseengine.service.helper.PageResultConverter;
import com.vose.voseengine.model.entity.JobArgs;
import com.vose.voseengine.repository.SiparisDetayRepository;
import com.vose.voseengine.service.helper.SepetCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SiparisService {
    private static final Logger log = LoggerFactory.getLogger(SiparisService.class);

    @Autowired
    private SepetCalculator cashier;
    @Autowired
    private SiparisRepository repository;
    @Autowired
    private SiparisDetayRepository detayRepository;

    @Autowired
    private JobManager jobManager;

    public PageResult<Siparis> findAllPaginated(int page, int perPage, long end, long start) {
        Pageable pageRequest = PageRequest.of(page-1, perPage, Sort.by(Sort.Order.desc("siparisTarihi")));
        PageResultConverter converter = new PageResultConverter();
        if (start > 0 && end > 0) {
            return converter.convert(repository.findBySiparisTarihiBetween(new Date(start), new Date(end), pageRequest));
        } else if (start > 0) {
            return converter.convert(repository.findBySiparisTarihiGreaterThanEqual(new Date(start), pageRequest));
        } else if (end > 0) {
            return converter.convert(repository.findBySiparisTarihiLessThanEqual(new Date(end), pageRequest));
        } else {
            return converter.convert(repository.findAll(pageRequest));
        }
    }

    public PageResult<Siparis> findByBayiIdPaginated(Long bayiId, int page, int perPage, long end, long start) {
        Pageable pageRequest = PageRequest.of(page-1, perPage, Sort.by(Sort.Order.desc("siparisTarihi")));
        PageResultConverter converter = new PageResultConverter();
        if (start > 0 && end > 0) {
            return converter.convert(repository.findByBayiIdAndSiparisTarihiBetween(bayiId, new Date(start), new Date(end), pageRequest));
        } else if (start > 0) {
            return converter.convert(repository.findByBayiIdAndSiparisTarihiGreaterThanEqual(bayiId, new Date(start), pageRequest));
        } else if (end > 0) {
            return converter.convert(repository.findByBayiIdAndSiparisTarihiLessThanEqual(bayiId, new Date(end), pageRequest));
        } else {
            return converter.convert(repository.findByBayiId(bayiId, pageRequest));
        }
    }

    public PageResult<Siparis> findByDurumPaginated(Siparis.Durum durum, int page, int perPage, long end, long start) {
        Pageable pageRequest = PageRequest.of(page-1, perPage, Sort.by(Sort.Order.desc("siparisTarihi")));
        PageResultConverter converter = new PageResultConverter();
        if (start > 0 && end > 0) {
            return converter.convert(repository.findBySiparisDurumAndSiparisTarihiBetween(durum, new Date(start), new Date(end), pageRequest));
        } else if (start > 0) {
            return converter.convert(repository.findBySiparisDurumAndSiparisTarihiGreaterThanEqual(durum, new Date(start), pageRequest));
        } else if (end > 0) {
            return converter.convert(repository.findBySiparisDurumAndSiparisTarihiLessThanEqual(durum, new Date(end), pageRequest));
        } else {
            return converter.convert(repository.findBySiparisDurum(durum, pageRequest));
        }
    }

    public Siparis findOneById(Long id) {
        Optional<Siparis> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Siparis "+id+" not found");
        return resultOptional.get();
    }

    public Siparis findOneById(Long bayiId, Long id) {
        return repository.findByBayiIdAndId(bayiId, id);
    }

    @Transactional
    public Siparis saveSiparis(MetaSiparis metaSiparis) {
        Siparis siparis = cashier.calculateSiparis(metaSiparis);
        List<SiparisDetay> items = siparis.getSiparisItems();
        repository.save(siparis);
        items.forEach(item -> item.setSiparisId(siparis.getId()));
        detayRepository.saveAll(items);
        return siparis;
    }

    @Transactional
    public Siparis updateSiparisDurum(Long siparisId, Siparis.Durum durum) {
        Siparis existing = getExisting(siparisId, repository);
        existing.setSiparisDurum(durum);
        existing.setSonGuncelleyen("_unknown_");
        if(durum == Siparis.Durum.ODEME_ONAYLANDI) {
            existing.setOdemeOnay(true);
            existing.setOdemeOnaylayan("_unknown_");
            existing.setOdemeOnayTarih(new Date());
            jobManager.produce(
                    Job.Type.ODEME_ONAY_ISLE,
                    new JobArgs.Builder().setSiparisId(siparisId).build(),
                    null
            );
        }
        return repository.save(existing);
    }

    protected Siparis getExisting(Long id, CrudRepository<Siparis, Long> repository) {
        Optional<Siparis> existingOptional = repository.findById(id);
        if(!existingOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existing entity with provided id:"+id+" in "+this.getClass().getSimpleName());
        }
        return existingOptional.get();
    }
}
