package com.vose.voseengine.model.converter;

import com.google.gson.Gson;
import com.vose.voseengine.model.entity.JobArgs;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class JobArgsConverter implements AttributeConverter<JobArgs, String> {

    private Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(JobArgs jobArgs) {
        return gson.toJson(jobArgs);
    }

    @Override
    public JobArgs convertToEntityAttribute(String jsonStr) {
        return gson.fromJson(jsonStr, JobArgs.class);
    }
}
