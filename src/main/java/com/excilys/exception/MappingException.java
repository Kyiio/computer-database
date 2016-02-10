package com.excilys.exception;

@SuppressWarnings("serial")
/**
 * Exception that can be thrown by the mapping class when there is a mapping issue
 * @author Herbaut Bastien
 */
public class MappingException extends RuntimeException {

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

}