package com.excilys.exception;

public class PageException extends RuntimeException {

	private static final long serialVersionUID = -4843013553385554872L;

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