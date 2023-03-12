package com.vose.voseengine.model.service;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtClaims {
    private String type;
    private String action;
    private Long id;
    private String issuer;
    private Date issuedAt;
    private Date expiresAt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public static JwtClaims fromJwt(DecodedJWT jwt) {
        JwtClaims result = new JwtClaims();
        result.setType(jwt.getClaim("type").asString());
        result.setAction(jwt.getClaim("action").asString());
        result.setId(jwt.getClaim("id").asLong());
        result.setIssuer(jwt.getIssuer());
        result.setIssuedAt(jwt.getIssuedAt());
        result.setExpiresAt(jwt.getExpiresAt());
        return result;
    }
}
