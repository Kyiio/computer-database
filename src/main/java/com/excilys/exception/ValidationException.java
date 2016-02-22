package com.excilys.exception;

/**
 * Exception that can be thrown by the DTO service or the service (DAO)
 * 
 * @author B. Herbaut
 */
public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = -2469410843259970165L;

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationException(Throwable cause) {
    super(cause);
  }
}