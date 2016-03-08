package com.excilys.dto.validation;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateConstraintValidator implements ConstraintValidator<Date, String> {

  @Override
  public void initialize(Date date) {

  }

  @Override
  public boolean isValid(String date, ConstraintValidatorContext context) {

    String format = "yyyy-MM-dd";

    if (date != null && date.length() > 0) {

      if (!date.matches("^\\d\\d\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$")) {
        return false;
      }

      try {
        new Timestamp(new SimpleDateFormat(format).parse(date).getTime()).toLocalDateTime();
      } catch (ParseException e) {
        return false;
      }
    }
    return true;
  }
}
