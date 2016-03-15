package com.excilys.dto.validation;

import com.excilys.exception.ValidationException;
import com.excilys.validator.ComputerValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<Date, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatValidator.class);

  @Autowired
  public ComputerValidator    computerValidator;

  @Override
  public void initialize(Date date) {

  }

  @Override
  public boolean isValid(String date, ConstraintValidatorContext context) {

    LOGGER.info("Start validation of the following date : " + date);

    try {
      computerValidator.checkDate(date);
    } catch (ValidationException e) {
      return false;
    }
    return true;
  }
}
