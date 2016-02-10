package com.excilys.exception;


@SuppressWarnings("serial")
/**
 * Exception that can be thrown by the DAO classes when there is an issue with a query
 * @author Herbaut Bastien
 */
public class DAOException extends RuntimeException {

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