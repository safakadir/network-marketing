package com.vose.voseengine.job.helper;

import com.vose.voseengine.model.entity.Bayi;

import java.util.List;

public class SponsorChain {
    private Bayi bayi;
    private List<Bayi> chain;

    public SponsorChain(Bayi bayi, List<Bayi> chain) {
        this.bayi = bayi;
        this.chain = chain;
    }

    public Bayi getSponsorAt(int derinlik) {
        if(derinlik > chain.size()) return null;
        return chain.get(derinlik-1);
    }

    public Bayi getAltBayiOf(Bayi sponsor) {
        if(chain.size() == 0) return null;
        if(sponsor.getId().longValue() == chain.get(0).getId().longValue()) return bayi;
        for(int i = 1; i < chain.size(); i++) {
            if(sponsor.getId().longValue() == chain.get(i).getId().longValue()) return chain.get(i-1);
        }
        return null;
    }

    public Bayi getSponsorOf(Bayi altBayi) {
        if(chain.size() == 0) return null;
        if(altBayi.getId().longValue() == bayi.getId().longValue()) return chain.get(0);
        for(int i = 0; i < chain.size()-1; i++) {
            if(altBayi.getId().longValue() == chain.get(i).getId().longValue()) return chain.get(i+1);
        }
        return null;
    }

    public int getDerinlikOf(Bayi sponsor) {
        return chain.indexOf(sponsor)+1;
    }

    public Bayi getBayi() {
        return bayi;
    }

    public List<Bayi> getSponsors() {
        return chain;
    }
}
