package com.excilys.exception;

@SuppressWarnings("serial")
/**
 * Exception that can be thrown by the ConnectionFactory when trying to connect to the database
 * @author Herbaut Bastien
 */
public class DAOConfigurationException extends RuntimeException {

    public DAOConfigurationException( String message ) {
        super( message );
    }

    public DAOConfigurationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOConfigurationException( Throwable cause ) {
        super( cause );
    }
}