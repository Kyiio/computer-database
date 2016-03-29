package com.excilys.validator;

import com.excilys.model.Computer;
import com.excilys.validator.exception.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Interface that provides methods to check if the data in a computer are consistent.
 * 
 * @author B. Herbaut
 */
@Component("computerValidator")
public class ComputerValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerValidator.class);

  /**
   * Method that check that the given id is positive. It throws a ValidationException if it is not
   * the case.
   * 
   * @param id The id that will be checked.
   */
  public void checkId(long id) {

    LOGGER.info("Check computer id : " + id);

    if (id <= 0) {
      throw new ValidationException("The computer id must be positive ! Given id is " + id);
    }
  }

  /**
   * Method that check that the computer name isn't null nor empty. If it is it throws a
   * ValidationException.
   * 
   * @param name The computer name that will be checked.
   */
  public void checkName(String name) {

    LOGGER.info("Check computer name : " + name);

    if (name == null || name.length() <= 0) {
      throw new ValidationException("The computer name must be set !");
    }
    if (name.length() > 100) {
      throw new ValidationException("The computer name is to long!");
    }
  }

  /**
   * Check date isnt to old.
   *
   * @param date the date
   */
  public void checkDateIsntToOld(LocalDate date) {

    LOGGER.info("Check computer date isn't to old: " + date);

    if (date != null) {

      LocalDate minDate = LocalDate.of(1970, 1, 2);

      if (date.isBefore(minDate)) {
        throw new ValidationException(
            "The given date is to old : " + date + "(before " + minDate + ")");
      }
    }
  }

  /**
   * Method that check if the two given dates respect the following rules: -introducedDate and
   * disontinuedDate can both be empty -discontinuedDate can't be set if introducedDate is'nt as
   * well -if both date are set, discontinuedDate must be after the introducedDate If it doesn't
   * respect those rules the method will throw a ValidationException.
   *
   * @param introducedDate Introduced date of the computer
   * @param discontinuedDate Discontinued date of the computer
   */
  public void checkDateConsitency(LocalDate introducedDate, LocalDate discontinuedDate) {

    LOGGER.info("Check date consistency for : introduced date" + introducedDate
        + " and discontinued date : " + discontinuedDate);

    if ((discontinuedDate != null && introducedDate == null) || (introducedDate != null
        && discontinuedDate != null && introducedDate.isAfter(discontinuedDate))) {
      throw new ValidationException(
          "The discontinued date is before the introduced one ! introduced: " + introducedDate
              + " discontinued: " + discontinuedDate);
    }
  }

  /**
   * Check computer.
   *
   * @param computer the computer
   */
  public void checkComputer(Computer computer) {

    LOGGER.info("Check computer " + computer);

    if (computer == null) {
      throw new ValidationException("Computer is null");
    }

    checkId(computer.getId());
    checkName(computer.getName());
    checkDates(computer.getIntroduced(), computer.getDiscontinued());
  }

  /**
   * Check dates.
   *
   * @param introduced the introduced
   * @param discontinued the discontinued
   */
  public void checkDates(LocalDate introduced, LocalDate discontinued) {

    LOGGER.info("Check dates : introduced " + introduced + " discontinued : " + discontinued);

    checkDateIsntToOld(introduced);
    checkDateIsntToOld(discontinued);
    checkDateConsitency(introduced, discontinued);
  }
}
