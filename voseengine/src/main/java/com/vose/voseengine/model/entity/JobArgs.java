package com.vose.voseengine.model.entity;

import com.vose.voseengine.Util;
import com.vose.voseengine.job.helper.PvCollection;

public class JobArgs {
    private Long bayiId;
    private Long altBayiId;
    private Long siparisId;
    private Double tutar;
    private Kazanc.Tur kazancTuru;
    private Double pv;
    private Double cv;
    private PvCollection pvCollection;
    private String monthKey;

    public static class Builder {
        private JobArgs instance;

        public Builder() {
            instance = new JobArgs();
        }

        public JobArgs build() {
            return instance;
        }

        public Builder setBayiId(Long bayiId) {
            instance.bayiId = bayiId;
            return this;
        }

        public Builder setAltBayiId(Long altBayiId) {
            instance.altBayiId = altBayiId;
            return this;
        }

        public Builder setSiparisId(Long siparisId) {
            instance.siparisId = siparisId;
            return this;
        }

        public Builder setTutar(Double tutar) {
            instance.tutar = Util.round(tutar, 2);
            return this;
        }

        public Builder setKazancTuru(Kazanc.Tur kazancTuru) {
            instance.kazancTuru = kazancTuru;
            return this;
        }

        public Builder setPv(Double pv) {
            instance.pv = Util.round(pv, 2);
            return this;
        }

        public Builder setCv(Double cv) {
            instance.cv = Util.round(cv, 2);
            return this;
        }

        public Builder setPvCollection(PvCollection pvCollection) {
            instance.pvCollection = pvCollection;
            return this;
        }

        public Builder setMonthKey(String monthKey) {
            instance.monthKey = monthKey;
            return this;
        }
    }

    public Long getBayiId() {
        return bayiId;
    }

    public Long getAltBayiId() {
        return altBayiId;
    }

    public Long getSiparisId() {
        return siparisId;
    }

    public Double getTutar() {
        return tutar;
    }

    public Kazanc.Tur getKazancTuru() {
        return kazancTuru;
    }

    public Double getPv() {
        return pv;
    }

    public Double getCv() {
        return cv;
    }

    public PvCollection getPvCollection() {
        return pvCollection;
    }

    public String getMonthKey() {
        return monthKey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("JobArgs{");
        if(bayiId != null) sb.append("bayiId="+bayiId+",");
        if(altBayiId != null) sb.append("altBayiId="+altBayiId+",");
        if(siparisId != null) sb.append("siparisId="+siparisId+",");
        if(tutar != null) sb.append("tutar="+tutar+",");
        if(kazancTuru != null) sb.append("kazancTuru="+ kazancTuru +",");
        if(pv != null) sb.append("pv="+ pv +",");
        if(cv != null) sb.append("cv="+ cv +",");
        if(pvCollection != null) sb.append("pvCollection="+pvCollection+",");
        if(monthKey != null) sb.append("monthKey="+monthKey+",");
        sb.append("}");
        return sb.toString();
    }
}
