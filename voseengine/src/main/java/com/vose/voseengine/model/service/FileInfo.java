package com.vose.voseengine.model.service;

public class FileInfo {
    private String originalName;
    private String filename;
    private String url;
    private long size;
    private String message;

    public FileInfo(String originalName, String filename, String url, long size, String message) {
        this.originalName = originalName;
        this.filename = filename;
        this.url = url;
        this.size = size;
        this.message = message;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
