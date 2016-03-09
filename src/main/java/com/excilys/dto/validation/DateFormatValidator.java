package com.excilys.dto.validation;

import com.excilys.exception.ValidationException;
import com.excilys.validator.ComputerValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<Date, String> {

  @Override
  public void initialize(Date date) {

  }

  @Override
  public boolean isValid(String date, ConstraintValidatorContext context) {

    try {
      ComputerValidator.checkDate(date, "yyyy-MM-dd");
    } catch (ValidationException e) {
      return false;
    }
    return true;
  }
}
