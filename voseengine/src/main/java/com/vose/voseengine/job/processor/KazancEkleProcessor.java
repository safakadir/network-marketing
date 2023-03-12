package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.model.entity.SubBayi;
import com.vose.voseengine.model.entity.Job;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.model.entity.Kazanc;
import com.vose.voseengine.repository.KazancRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KazancEkleProcessor implements JobProcessor {
    @Autowired
    private KazancRepository kazancRepository;
    @Autowired
    private BayiRepository bayiRepository;

    @Transactional
    @Override
    public void process(Job job) {
        Kazanc kazanc = new Kazanc();
        kazanc.setBayi(bayiRepository.findSubBayiById(job.getBayiIdArg()));
        kazanc.setMiktar(job.getTutarArg());
        kazanc.setKazancTuru(job.getKazancTuru());
        SubBayi altBayi = bayiRepository.findSubBayiById(job.getAltBayiIdArg());
        kazanc.setAltBayi(altBayi);
        kazancRepository.save(kazanc);
    }
}
