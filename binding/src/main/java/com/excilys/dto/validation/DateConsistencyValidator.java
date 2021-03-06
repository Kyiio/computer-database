package com.excilys.dto.validation;

import com.excilys.dto.ComputerDto;
import com.excilys.validator.exception.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class DateConsistencyValidator implements ConstraintValidator<DateConsistency, ComputerDto> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateConsistencyValidator.class);

  @Autowired
  public ComputerDtoValidator computerDtoValidator;

  @Override
  public void initialize(DateConsistency constraintAnnotation) {

  }

  @Override
  public boolean isValid(ComputerDto computerDto, ConstraintValidatorContext context) {

    LOGGER.info("Starting date consistency validation for the following computer :" + computerDto);

    try {
      computerDtoValidator.checkDateConsitency(computerDto.getIntroducedDate(),
          computerDto.getDiscontinuedDate());
    } catch (ValidationException e) {
      return false;
    }

    return true;
  }

}
