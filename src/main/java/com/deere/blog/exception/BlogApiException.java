package com.deere.blog.exception;

import lombok.Getter;

@Getter
public class BlogApiException extends RuntimeException{

    private static final long serialVersionUID = 3077736260625437070L;

    private final String message;

    private final int messageCode;

    public BlogApiException(Throwable cause, int messageCode, String message) {
        super(message, cause);
        this.messageCode = messageCode;
        this.message = message ;
    }

    public BlogApiException(int status, String errorMessage) {

        super(errorMessage);
        this.messageCode = status;
        this.message = errorMessage ;
    }

}
