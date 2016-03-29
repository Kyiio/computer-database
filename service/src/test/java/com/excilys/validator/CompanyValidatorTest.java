package com.excilys.validator;

import com.excilys.validator.exception.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./core-context-test.xml" })
public class CompanyValidatorTest {

  @Resource(name = "companyValidator")
  private CompanyValidator companyValidator;

  @Test(expected = ValidationException.class)
  public void checkNegativeId() {
    companyValidator.checkId(-1);
  }

  @Test
  public void checkId() {
    companyValidator.checkId(1);
  }

  @Test(expected = ValidationException.class)
  public void checkNameNull() {
    companyValidator.checkName(null);
  }

  @Test(expected = ValidationException.class)
  public void checkNameEmpty() {
    companyValidator.checkName("");
  }

  @Test
  public void checkName() {
    companyValidator.checkName("A company");
  }

}
