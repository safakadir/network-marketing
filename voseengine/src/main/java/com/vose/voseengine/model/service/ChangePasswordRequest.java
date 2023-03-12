package com.vose.voseengine.model.service;

public class ChangePasswordRequest {
    private long bayiId; // Default null. If has a value, then ADMIN is changing some other bayi's password.
    private String oldPassword;
    private String newPassword;

    public long getBayiId() {
        return bayiId;
    }

    public void setBayiId(long bayiId) {
        this.bayiId = bayiId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
