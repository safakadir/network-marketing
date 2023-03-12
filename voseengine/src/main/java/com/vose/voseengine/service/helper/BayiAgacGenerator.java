package com.vose.voseengine.service.helper;

import com.vose.voseengine.model.entity.Bayi;
import com.vose.voseengine.model.service.BayiAgac;
import com.vose.voseengine.repository.BayiRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BayiAgacGenerator {
    public BayiAgac generate(Bayi bayi, BayiRepository bayiRepository, int kalanDerinlik) {
        if(bayi == null) return null;
        BayiAgac agac = new BayiAgac();
        agac.setName(bayi.getIdentity());
        agac.setId(bayi.getId());

        if(kalanDerinlik == 0) {
            agac.setChildren(null);
            return agac;
        }

        List<BayiAgac> children = null;
        List<Bayi> kollar = bayiRepository.findBySponsorIdOrderByKolSiraAsc(bayi.getId());
        if(kollar != null && kollar.size() > 0) {
            children = new ArrayList<>();
            for (Bayi kol : kollar) {
                children.add(generate(kol, bayiRepository, kalanDerinlik-1));
            }
        }
        agac.setChildren(children);
        return agac;
    }
}
