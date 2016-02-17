package com.excilys.exception;

/**
 * Exception that can be thrown by the mapping class when there is a mapping issue
 * @author B. Herbaut
 */
public class MappingException extends RuntimeException {

	private static final long serialVersionUID = -5441230150444670415L;

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