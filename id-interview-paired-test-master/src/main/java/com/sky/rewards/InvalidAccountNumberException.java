package com.sky.rewards;

public class InvalidAccountNumberException extends Exception {

    private String service;
    private Throwable t;

    public InvalidAccountNumberException() {

    }

    public InvalidAccountNumberException(String service, Throwable t) {
        this.service = service;
        this.t = t;
    }
}
