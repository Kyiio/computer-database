package com.excilys.exception;

/**
 * Exception that can be thrown if their is an error while we manipulate the data of a page object.
 * 
 * @author B. Herbaut
 */
public class PageException extends RuntimeException {

  private static final long serialVersionUID = -4843013553385554872L;

  public PageException(String message) {
    super(message);
  }

  public PageException(String message, Throwable cause) {
    super(message, cause);
  }

  public PageException(Throwable cause) {
    super(cause);
  }

}