package com.vose.voseengine.job.helper;

import com.vose.voseengine.model.entity.Bayi;
import com.vose.voseengine.repository.BayiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SponsorChainFormer {

    @Value("${vose.maxDerinlik}")
    private int maxDerinlik;

    @Autowired
    private BayiRepository bayiRepository;

    public SponsorChain form(Bayi bayi) {
        List<Bayi> chain = new ArrayList<>();

        Bayi currentBayi = bayi;
        while(currentBayi.getSponsorId() != null && currentBayi.getSponsorId() > 0)
        {
            Bayi sponsorBayi = bayiRepository.findById(currentBayi.getSponsorId()).get();
            chain.add(sponsorBayi);
            if(bayi.getDerinlik()-sponsorBayi.getDerinlik() == maxDerinlik) {
                break;
            }
            currentBayi = sponsorBayi;
        }

        return new SponsorChain(bayi, chain);
    }
}
