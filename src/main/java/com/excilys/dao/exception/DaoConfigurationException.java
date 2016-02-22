package com.excilys.dao.exception;

/**
 * Exception that can be thrown by the ConnectionFactory when trying to connect to the database.
 * 
 * @author B. Herbaut
 */
public class DaoConfigurationException extends RuntimeException {

  private static final long serialVersionUID = 8499700685519784433L;

  public DaoConfigurationException(String message) {
    super(message);
  }

  public DaoConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DaoConfigurationException(Throwable cause) {
    super(cause);
  }
}