package com.excilys.validator;

import com.excilys.model.QueryParameters;
import com.excilys.validator.exception.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./service-context-test.xml" })
public class QueryParamatersValidatorTest {

  public void testCheckPageNumber() {
    QueryParametersValidator.checkPageNumber(1);
  }

  @Test(expected = ValidationException.class)
  public void testCheckPageNumberFail() {
    QueryParametersValidator.checkPageNumber(0);
  }

  public void testCheckPageSize() {
    QueryParametersValidator.checkPageSize(1);
  }

  @Test(expected = ValidationException.class)
  public void testCheckPageSizeFail() {
    QueryParametersValidator.checkPageSize(0);
  }

  public void testValidateQueryParamtersObject() {
    QueryParameters queryParams = new QueryParameters();
    QueryParametersValidator.validateQueryParameters(queryParams);
  }

  @Test(expected = ValidationException.class)
  public void testValidateQueryParamtersObjectNull() {
    QueryParametersValidator.validateQueryParameters(null);
  }
}
