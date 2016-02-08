package com.excilys.exception;

@SuppressWarnings("serial")
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