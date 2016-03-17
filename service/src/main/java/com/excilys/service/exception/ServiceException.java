package com.excilys.service.exception;

/**
 * Exception that can be thrown while using a service
 * 
 * @author B. Herbaut
 */
public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = -7587566368488117547L;

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }
}