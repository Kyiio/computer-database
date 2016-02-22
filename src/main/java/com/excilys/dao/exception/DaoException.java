package com.excilys.dao.exception;

/**
 * Exception that can be thrown by the DAO classes when there is an issue with a query.
 * 
 * @author B. Herbaut
 */
public class DaoException extends RuntimeException {

  private static final long serialVersionUID = 265352034167176911L;

  public DaoException(String message) {
    super(message);
  }

  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }

  public DaoException(Throwable cause) {
    super(cause);
  }
}