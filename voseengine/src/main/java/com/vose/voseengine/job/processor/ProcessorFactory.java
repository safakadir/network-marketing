package com.vose.voseengine.job.processor;

import com.vose.voseengine.job.JobProcessor;
import com.vose.voseengine.model.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessorFactory {
    @Autowired
    private AySonuProcessor aySonuProcessor;
    @Autowired
    private AySonuBayiProcessor aySonuBayiProcessor;
    @Autowired
    private CuzdanaIsleProcessor cuzdanaIsleProcessor;
    @Autowired
    private CuzdanaIsleBayiProcessor cuzdanaIsleBayiProcessor;
    @Autowired
    private OdemeOnayProcessor odemeOnayProcessor;
    @Autowired
    private KariyerAtlaProcessor kariyerAtlaProcessor;
    @Autowired
    private KayitTamamlaProcessor kayitTamamlaProcessor;
    @Autowired
    private KariyerCheckProcessor kariyerCheckProcessor;
    @Autowired
    private KazancEkleProcessor kazancEkleProcessor;
    @Autowired
    private PuanEkleProcessor puanEkleProcessor;

    public JobProcessor getProcessorFor(Job job) {
        switch (job.getType()) {
            case AY_SONU:
                return aySonuProcessor;
            case AY_SONU_BAYI:
                return aySonuBayiProcessor;
            case CUZDANA_ISLE:
                return cuzdanaIsleProcessor;
            case CUZDANA_ISLE_BAYI:
                return cuzdanaIsleBayiProcessor;
            case ODEME_ONAY_ISLE:
                return odemeOnayProcessor;
            case KARIYER_ATLA:
                return kariyerAtlaProcessor;
            case KAYIT_TAMAMLA:
                return kayitTamamlaProcessor;
            case KARIYER_CHECK:
                return kariyerCheckProcessor;
            case KAZANC_EKLE:
                return kazancEkleProcessor;
            case PUAN_EKLE:
                return puanEkleProcessor;
            default:
                return null;
        }
    }
}
