package com.excilys.dto.validation;

import com.excilys.dto.ComputerDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateConsistencyValidator implements ConstraintValidator<DateConsistency, ComputerDto> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateConsistencyValidator.class);

  @Override
  public void initialize(DateConsistency constraintAnnotation) {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean isValid(ComputerDto computerDto, ConstraintValidatorContext context) {

    LOGGER.info("Starting date consistency validation");

    LocalDate discontinuedDate = null;
    LocalDate introducedDate = null;

    String introducedStr = computerDto.getIntroducedDate();
    String discontinuedStr = computerDto.getDiscontinuedDate();

    if (introducedStr != null && introducedStr.length() > 0) {
      try {
        introducedDate =
            new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(introducedStr).getTime())
            .toLocalDateTime().toLocalDate();
      } catch (ParseException e) {
        LOGGER.debug("Error while parsing introduced date :" + introducedStr);
        return false;
      }
    }

    if (discontinuedStr != null && discontinuedStr.length() > 0) {
      try {
        discontinuedDate =
            new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(discontinuedStr).getTime())
            .toLocalDateTime().toLocalDate();
      } catch (ParseException e) {
        LOGGER.debug("Error while parsing discontinued date :" + discontinuedStr);
        return false;
      }
    }

    if ((discontinuedDate != null && introducedDate == null) || (introducedDate != null
        && discontinuedDate != null && introducedDate.isAfter(discontinuedDate))) {
      LOGGER.info("Problem with date consistency");
      return false;
    }

    return true;
  }

}
