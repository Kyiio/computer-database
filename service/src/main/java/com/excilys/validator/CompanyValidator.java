package com.excilys.validator;

import com.excilys.validator.exception.ValidationException;

import org.springframework.stereotype.Component;

/**
 * Interface that provides methods to check if the data in a company are consistent.
 * 
 * @author B. Herbaut
 */
@Component("companyValidator")
public class CompanyValidator {

  /**
   * Method that check if the company's id isn't negative or 0. If it is it throws a
   * ValidtionException
   * 
   * @param id The company id that will be checked.
   */
  public void checkId(long id) {
    if (id <= 0) {
      throw new ValidationException("The company id must be positive ! Given id is " + id);
    }
  }

  /**
   * Method that check if the company name isn't empty or null. If it is it throws a
   * ValidationException.
   * 
   * @param name The company name that will be checked.
   */
  public void checkName(String name) {
    if (name == null || name.length() <= 0) {
      throw new ValidationException("The company name must be set !");
    }
  }

}
