package com.vose.voseengine.job;

public class JobProcessException extends RuntimeException {
    public JobProcessException(String message) {
        super(message);
    }
    public JobProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
