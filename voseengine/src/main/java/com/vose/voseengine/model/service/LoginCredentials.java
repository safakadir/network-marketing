package com.vose.voseengine.model.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginCredentials {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

   @JsonIgnore
    public Long getUsernameLong() {
        return Long.parseLong(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
