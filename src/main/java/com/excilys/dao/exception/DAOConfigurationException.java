package com.excilys.dao.exception;

/**
 * Exception that can be thrown by the ConnectionFactory when trying to connect to the database
 * @author Herbaut Bastien
 */
public class DAOConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 8499700685519784433L;

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