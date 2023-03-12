package com.vose.voseengine.model.converter;

import com.google.gson.Gson;
import com.vose.voseengine.model.entity.Adres;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AdresConverter implements AttributeConverter<Adres, String> {

    private Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(Adres adres) {
        return gson.toJson(adres);
    }

    @Override
    public Adres convertToEntityAttribute(String jsonStr) {
        return gson.fromJson(jsonStr, Adres.class);
    }
}
