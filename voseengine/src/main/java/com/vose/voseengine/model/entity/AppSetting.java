package com.vose.voseengine.model.entity;


import com.google.gson.Gson;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ar_app_settings")
public class AppSetting  implements Identable<String> {

    @Id
    private String name;
    private String value;

    public AppSetting() {
    }

    public AppSetting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getId() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getString() {
        return value;
    }

    public Integer getInt() {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public Double getDouble() {
        try {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public <T> T getFromJson(Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(value, clazz);
    }
}
