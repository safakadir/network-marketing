package com.vose.voseengine.model.service;

import com.vose.voseengine.model.entity.Admin;
import com.vose.voseengine.model.entity.Bayi;

public class Login {

    private Admin admin;
    private Bayi bayi;
    private String accessToken;
    private String refreshToken;
    private long accessExpiresAt;
    private long refreshExpiresAt;
    private Type type;

    public Login(Admin admin, String accessToken, String refreshToken, long accessExpiresAt, long refreshExpiresAt) {
        this.admin = admin;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessExpiresAt = accessExpiresAt;
        this.refreshExpiresAt = refreshExpiresAt;
        this.type = Type.ADMIN;
    }

    public Login(Bayi bayi, String accessToken, String refreshToken, long accessExpiresAt, long refreshExpiresAt) {
        this.bayi = bayi;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessExpiresAt = accessExpiresAt;
        this.refreshExpiresAt = refreshExpiresAt;
        this.type = Type.BAYI;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Bayi getBayi() {
        return bayi;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getAccessExpiresAt() {
        return accessExpiresAt;
    }

    public long getRefreshExpiresAt() {
        return refreshExpiresAt;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        ADMIN,
        BAYI
    }
}
