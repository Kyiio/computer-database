package com.excilys.dao.exception;

/**
 * Exception that can be thrown by the DAO classes when there is an issue with a
 * query.
 * 
 * @author B. Herbaut
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 265352034167176911L;

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}