package com.excilys.exception;

@SuppressWarnings("serial")
/**
 * Exception that can be thrown when input from the CLI do not match the constraints
 * @author Herbaut Bastien
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}