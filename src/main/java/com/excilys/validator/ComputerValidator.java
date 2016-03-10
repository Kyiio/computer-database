package com.excilys.validator;

import com.excilys.exception.ValidationException;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Interface that provides methods to check if the data in a computer are consistent.
 * 
 * @author B. Herbaut
 */
public interface ComputerValidator {

  /**
   * Method that check that the given id is positive. It throws a ValidationException if it is not
   * the case.
   * 
   * @param id The id that will be checked.
   */
  public static void checkId(long id) {
    if (id <= 0) {
      throw new ValidationException("The computer id must be positive ! Given id is " + id);
    }
  }

  /**
   * Method that check if the given string isn't null, nor empty and contains only an int. If not
   * throws a ValidationException.
   * 
   * @param idString The string that will be checked.
   */
  public static void checkId(String idString) {
    if (idString == null || idString.length() == 0) {
      throw new ValidationException("The computer id must be an int ! Given id is " + idString);
    }

    int id = 0;
    try {
      id = Integer.parseInt(idString);
    } catch (Exception e) {
      throw new ValidationException("Error parsing the given id ! Given id is " + idString);
    }

    checkId(id);
  }

  /**
   * Method that check that the computer name isn't null nor empty. If it is it throws a
   * ValidationException.
   * 
   * @param name The computer name that will be checked.
   */
  public static void checkName(String name) {
    if (name == null || name.length() <= 0) {
      throw new ValidationException("The computer name must be set !");
    }
  }

  /**
   * Method that check if the given String data is null, empty or contains only a date with valid
   * format. If it doesn't respect those condition, it throws a ValidationException
   *
   * @param date The will be checked
   */
  public static void checkDate(String date) {

    LocalDate currentDate = checkDateFormat(date);
    checkDateIsntToOld(currentDate);
  }

  /**
   * Check date format.
   *
   * @param date the date
   * @return the local date
   */
  public static LocalDate checkDateFormat(String date) {

    MessageSource messageSource;
    try (ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("applicationContext.xml")) {

      messageSource = applicationContext.getBean("messageSource", MessageSource.class);
    }

    String format = messageSource.getMessage("date.format", null, LocaleContextHolder.getLocale());
    String regex =
        messageSource.getMessage("date.java.regex", null, LocaleContextHolder.getLocale());

    LocalDate localDate = null;

    if (date != null && date.length() > 0) {

      if (!date.matches(regex)) {
        throw new ValidationException(
            "The given date " + date + " isn't matching the format : " + format);
      }

      try {
        localDate = new Timestamp(new SimpleDateFormat(format).parse(date).getTime())
            .toLocalDateTime().toLocalDate();
      } catch (ParseException e) {
        throw new ValidationException(
            "The given date " + date + " isn't matching the format : " + format);
      }
    }

    return localDate;
  }

  /**
   * Check date isnt to old.
   *
   * @param date the date
   */
  public static void checkDateIsntToOld(LocalDate date) {

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
  public static void checkDateConsitency(LocalDate introducedDate, LocalDate discontinuedDate) {
    if ((discontinuedDate != null && introducedDate == null) || (introducedDate != null
        && discontinuedDate != null && introducedDate.isAfter(discontinuedDate))) {
      throw new ValidationException(
          "The discontinued date is before the introduced one ! introduced: " + introducedDate
              + " discontinued: " + discontinuedDate);
    }
  }

  /**
   * Check date consitency.
   *
   * @param introducedStr the introduced str
   * @param discontinuedStr the discontinued str
   */
  public static void checkDateConsitency(String introducedStr, String discontinuedStr) {

    LocalDate introducedDate;
    LocalDate discontinuedDate;

    try {
      introducedDate = checkDateFormat(introducedStr);
      discontinuedDate = checkDateFormat(discontinuedStr);
    } catch (ValidationException e) {
      return;
    }

    checkDateConsitency(introducedDate, discontinuedDate);
  }
}
