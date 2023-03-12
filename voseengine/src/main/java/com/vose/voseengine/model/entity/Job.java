package com.vose.voseengine.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vose.voseengine.Util;
import com.vose.voseengine.model.converter.JobArgsConverter;
import com.vose.voseengine.job.helper.PvCollection;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ar_jobs")
public class Job implements Identable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rootId;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Convert(converter = JobArgsConverter.class)
    private JobArgs args;
    private String generation;
    private boolean processed;
    @CreationTimestamp
    private Date createDate;
    private Date processDate;
    private boolean success;
    @OneToOne
    private FailLog failLog;

    public static Job create(Type type, JobArgs args, Job parentJob) {
        Job instance = new Job();
        instance.type = type;
        instance.args = args;
        if(parentJob != null) {
            String generationPrefix = parentJob.getGeneration() == null ? "" : parentJob.getGeneration()+",";
            instance.generation = generationPrefix+parentJob.getId();

            String[] parents = instance.generation.split(",");
            instance.rootId = Long.parseLong(parents[0]);
        }
        return instance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public JobArgs getArgs() {
        return args;
    }

    public void setArgs(JobArgs args) {
        this.args = args;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public boolean getProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public FailLog getFailLog() {
        return failLog;
    }

    public void setFailLog(FailLog failLog) {
        this.failLog = failLog;
    }

    @JsonIgnore
    public Long getBayiIdArg() {
        return args.getBayiId();
    }

    @JsonIgnore
    public Long getAltBayiIdArg() {
        return args.getAltBayiId();
    }

    @JsonIgnore
    public Long getSiparisIdArg() {
        return args.getSiparisId();
    }

    @JsonIgnore
    public Double getTutarArg() {
        return args.getTutar();
    }

    @JsonIgnore
    public Kazanc.Tur getKazancTuru() {
        return args.getKazancTuru();
    }

    @JsonIgnore
    public Double getPvArg() {
        return args.getPv();
    }

    @JsonIgnore
    public Double getCvArg() {
        return args.getCv();
    }

    @JsonIgnore
    public PvCollection getPvCollectionArg() {
        return args.getPvCollection();
    }

    @JsonIgnore
    public String getMonthKeyArg() {
        return args.getMonthKey();
    }

    public Long getRoot() {
        if(Util.isNullOrEmpty(generation)) return null;
        String[] parents = generation.split(",");
        return Long.parseLong(parents[0]);
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", type=" + type +
                ", args=" + args +
                ", createDate=" + createDate +
                '}';
    }

    public enum Type {
        KAYIT_TAMAMLA,
        ODEME_ONAY_ISLE,
        KARIYER_ATLA,
        AY_SONU,
        AY_SONU_BAYI,
        KAZANC_EKLE,
        PUAN_EKLE,
        KARIYER_CHECK,
        CUZDANA_ISLE,
        CUZDANA_ISLE_BAYI
    }
}
