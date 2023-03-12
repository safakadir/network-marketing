package com.vose.voseengine.service;

import com.vose.voseengine.job.JobManager;
import com.vose.voseengine.job.helper.PvCollection;
import com.vose.voseengine.job.helper.PvCollector;
import com.vose.voseengine.model.entity.*;
import com.vose.voseengine.model.service.PageResult;
import com.vose.voseengine.model.service.PuanEkleRequest;
import com.vose.voseengine.repository.AppSettingRepository;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.model.service.BayiAgac;
import com.vose.voseengine.service.helper.BayiAgacGenerator;
import com.vose.voseengine.service.helper.PageResultConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class BayiService extends CrudService<Bayi> {
    private static final Logger log = LoggerFactory.getLogger(BayiService.class);

    @Value("${vose.maxDerinlik}")
    private int maxDerinlik;

    @Autowired
    private BayiRepository repository;

    @Autowired
    private AppSettingRepository appSettingRepository;

    @Autowired
    private PvCollector pvCollector;

    @Autowired
    private JobManager jobManager;

    @Override
    public List<Bayi> findAll() {
        throw new RuntimeException("findAll method not implemented for paginated entity.");
    }

    public PageResult<Bayi> findPaginated(int page, int perPage) {
        Pageable pageRequest = PageRequest.of(
                page-1, perPage,
                Sort.by(Sort.Order.asc("derinlik"), Sort.Order.asc("sponsorId"), Sort.Order.asc("kolSira"))
        );
        PageResultConverter converter = new PageResultConverter();
        return converter.convert(repository.findAll(pageRequest));
    }

    public Bayi findOneById(Long id) {
        Optional<Bayi> resultOptional = repository.findById(id);
        if(!resultOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bayi "+id+" not found");
        return resultOptional.get();
    }

    public SubBayi findSubBayiById(Long id) {
        return repository.findSubBayiById(id);
    }

    public List<SubBayi> findByBayiIds(List<Long> ids) {
        return repository.findByIdIn(ids);
    }

    public List<SubBayi> findBySearchParam(String searchParam) {
        Map<Long, SubBayi> resultMap = new HashMap<>();
        for(String term : searchParam.split(" ")) {
            Long id = -1L;
            try {
                id = Long.parseLong(term);
            } catch (Exception e) {}
            if(id > 0) {
                repository.findTop10ByIdLike(term).forEach(b -> resultMap.put(b.getId(), b));
            }
            else {
                repository.findTop10ByIsimLike(term).forEach(b -> resultMap.put(b.getId(), b));;
                repository.findTop10BySoyisimLike(term).forEach(b -> resultMap.put(b.getId(), b));;
            }
        }
        return new ArrayList<>(resultMap.values());
    }

    @Transactional
    public Bayi addEntity(Bayi bayi) {

        if(bayi.getSponsorId() != null) {
            Bayi sponsor;
            try {
                sponsor = findOneById(bayi.getSponsorId());
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No sponsor with given id:"+bayi.getSponsorId());
            }
            Bayi prevSibling = repository.findTopBySponsorIdOrderByKolSiraDesc(bayi.getSponsorId());
            if(prevSibling == null) {
                bayi.setKolSira(1);
            }
            else {
                bayi.setKolSira(prevSibling.getKolSira()+1);
            }
            bayi.setDerinlik(sponsor.getDerinlik()+1);
        }
        return repository.save(bayi);
    }

    @Transactional
    public Bayi updateEntity(Bayi bayi) {
        Bayi existing = getExisting(bayi.getId(), repository);
        if(bayi.getSponsorId() != existing.getSponsorId()) {
            if(bayi.getSponsorId() == null) {
                bayi.setDerinlik(1);
                bayi.setKolSira(1);
            }
            else {
                Bayi sponsor = repository.findById(bayi.getSponsorId()).get();
                Bayi prevSibling = repository.findTopBySponsorIdOrderByKolSiraDesc(bayi.getSponsorId());
                if (prevSibling == null) {
                    bayi.setKolSira(1);
                } else {
                    bayi.setKolSira(prevSibling.getKolSira() + 1);
                }
                bayi.setDerinlik(sponsor.getDerinlik() + 1);
            }
        }
        bayi.setSifre(existing.getSifre());
        return repository.save(bayi);
    }

    @Transactional
    public boolean deleteEntity(Long bayiId) {
        Bayi existing = getExisting(bayiId, repository);
        // ???
        repository.deleteById(bayiId);
        return true;
    }

    public Kariyer getNextKariyer(Long bayiId) {
        return repository.getNextKariyer(bayiId);
    }

    public BayiAgac getBayiAgac(Long bayiId) {
        Bayi bayi = repository.findBayiById(bayiId);
        BayiAgacGenerator generator = new BayiAgacGenerator();
        return generator.generate(bayi, repository, maxDerinlik);
    }

    public boolean isAltBayi(Long sponsorId, Long bayiId) {
        Bayi bayi = findOneById(bayiId);
        if(bayi == null) return false;
        while(bayi.getSponsorId() != null) {
            if(bayi.getSponsorId().equals(sponsorId)) return true;
            bayi = findOneById(bayi.getSponsorId());
        }
        return false;
    }

    public boolean isDirectAltBayi(Long sponsorId, Long bayiId) {
        Bayi bayi = findOneById(bayiId);
        if(bayi == null) return false;
        return bayi.getSponsorId() != null && bayi.getSponsorId().equals(sponsorId);
    }

    public PvCollection getPvCollection(Long bayiId) {
        Bayi bayi = findOneById(bayiId);
        Kariyer nextKariyer = getNextKariyer(bayiId);
        if(nextKariyer.getBaslangicPaket()) return null;
        PvCollection pvCollection = pvCollector.collect(bayiId, bayi.getPuanlar(), nextKariyer.getKariyerPuan(), getMaxSingleCoef());
        return pvCollection;
    }


    public Job requestPuanEkle(PuanEkleRequest request) {
        if(!isDirectAltBayi(request.getBayiId(), request.getAltBayiId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, request.getAltBayiId()+" is not direct child of "+request.getBayiId());
        }
        return jobManager.produce(
                Job.Type.PUAN_EKLE,
                new JobArgs.Builder()
                        .setBayiId(request.getBayiId())
                        .setAltBayiId(request.getAltBayiId())
                        .setPv(request.getPv())
                        .setCv(request.getCv())
                        .build(),
                null
        );
    }

    private double getMaxSingleCoef() {
        Optional<AppSetting> settingOptional = appSettingRepository.findById("pvMaxSinglePercentage");
        if(!settingOptional.isPresent() || settingOptional.get().getInt() == null) {
            log.warn("APP SETTING pvMaxSinglePercentage NOT SET. Using default value 40!");
            return 0.4;
        }
        return settingOptional.get().getInt()/100.0;
    }
}
