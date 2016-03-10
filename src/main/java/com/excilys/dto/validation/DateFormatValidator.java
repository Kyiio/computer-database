package com.excilys.dto.validation;

import com.excilys.exception.ValidationException;
import com.excilys.validator.ComputerValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<Date, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatValidator.class);

  @Override
  public void initialize(Date date) {

  }

  @Override
  public boolean isValid(String date, ConstraintValidatorContext context) {

    LOGGER.info("Start validation of the following date : " + date);

    try {
      ComputerValidator.checkDate(date);
    } catch (ValidationException e) {
      return false;
    }
    return true;
  }
}
