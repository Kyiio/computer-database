package com.excilys.validator;

import com.excilys.model.QueryParameters;
import com.excilys.validator.exception.ValidationException;

/**
 * This interface offers methods to check each fild of a QueryParameters object.
 * 
 * @author B. Herbaut
 */
public interface QueryParametersValidator {

  /**
   * Method that check if the given offset isn't inferior to 1. If not it throws a
   * ValidationException
   *
   * @param pageSize the page size
   */
  public static void checkPageSize(int pageSize) {
    if (pageSize < 1) {
      throw new ValidationException("Offset can't be inferior to 1 : " + pageSize);
    }
  }

  /**
   * Method that check if the pageNumber isn't inferior to 1 If not it throws a ValidationException.
   *
   * @param pageNumber the page number
   */
  public static void checkPageNumber(int pageNumber) {
    if (pageNumber < 1) {
      throw new ValidationException("Page number can't be inferior to 1 : " + pageNumber);
    }
  }

  /**
   * Method that validate the content of a QueryParameters object.
   *
   * @param queryParameters the query parameters
   */
  public static void validateQueryParameters(QueryParameters queryParameters) {

    if (queryParameters == null) {
      throw new ValidationException("Query parameters can't be null");
    }

    checkPageSize(queryParameters.getPageSize());
    checkPageNumber(queryParameters.getPageNumber());
  }

}
