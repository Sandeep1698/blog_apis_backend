package com.deere.blog.exception;

public class ApiException extends RuntimeException {
    String msg;

    public ApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ApiException() {
        super();
    }
}
