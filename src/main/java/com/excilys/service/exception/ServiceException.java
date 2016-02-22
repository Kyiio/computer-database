package com.excilys.service.exception;

import com.excilys.service.CompanyDtoService;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerDtoService;
import com.excilys.service.ComputerService;

/**
 * Exception that can be thrown while using {@link ComputerService}, {@link ComputerDtoService},
 * {@link CompanyService} or {@link CompanyDtoService}
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