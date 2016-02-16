package com.excilys.exception;

@SuppressWarnings("serial")
public class PageException extends RuntimeException {

    public PageException(String message) {
        super(message);
    }

    public PageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageException(Throwable cause) {
        super(cause);
    }

}