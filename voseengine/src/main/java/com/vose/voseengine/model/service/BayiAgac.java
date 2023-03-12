package com.vose.voseengine.model.service;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BayiAgac {
    private String name;
    private Long id;
    private List<BayiAgac> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BayiAgac> getChildren() {
        return children;
    }

    public void setChildren(List<BayiAgac> children) {
        this.children = children;
    }
}
