package com.excilys.dto.validation;

import com.excilys.validator.ComputerValidator;
import com.excilys.validator.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

@Component
public class ComputerDtoValidator {

  @Autowired
  public MessageSource      messageSource;

  @Autowired
  private ComputerValidator computerValidator;

  /**
   * Method that check that the given id is positive. It throws a ValidationException if it is not
   * the case.
   * 
   * @param id The id that will be checked.
   */
  public void checkId(long id) {
    computerValidator.checkId(id);
  }

  /**
   * Method that check that the computer name isn't null nor empty. If it is it throws a
   * ValidationException.
   * 
   * @param name The computer name that will be checked.
   */
  public void checkName(String name) {
    computerValidator.checkName(name);
  }

  /**
   * Method that check if the given String data is null, empty or contains only a date with valid
   * format. If it doesn't respect those condition, it throws a ValidationException
   *
   * @param date The will be checked
   */
  public void checkDate(String date) {

    LocalDate currentDate = checkDateFormat(date);
    computerValidator.checkDateIsntToOld(currentDate);
  }

  /**
   * Check date consitency.
   *
   * @param introducedStr the introduced str
   * @param discontinuedStr the discontinued str
   */
  public void checkDateConsitency(String introducedStr, String discontinuedStr) {

    LocalDate introducedDate;
    LocalDate discontinuedDate;

    // We use a try catch because the purpose of this method is to check the consistency of the date
    // not the format, and so we don't want to raise an exception when the format is wrong
    try {
      introducedDate = checkDateFormat(introducedStr);
      discontinuedDate = checkDateFormat(discontinuedStr);
    } catch (ValidationException e) {
      return;
    }

    computerValidator.checkDateConsitency(introducedDate, discontinuedDate);
  }

  /**
   * Check date format.
   *
   * @param date the date
   * @return the local date
   */
  public LocalDate checkDateFormat(String date) {

    Locale locale = LocaleContextHolder.getLocale();
    String format = messageSource.getMessage("date.format", null, locale);
    String regex = messageSource.getMessage("date.java.regex", null, locale);

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

}
