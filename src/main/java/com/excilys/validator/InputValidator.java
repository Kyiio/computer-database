package com.excilys.validator;

import com.excilys.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class offers method that check if a String can be matched to a certain type.
 * 
 * @author B. Herbaut
 */
public interface InputValidator {

  /**
   * Method that check that the input is an integer.
   *
   * @param input
   *          The string we want to check
   */
  public static void isInt(String input) {
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher matcher = pattern.matcher(input);

    if (!matcher.find()) {
      throw new ValidationException("The given input isn't an int " + input);
    }
  }

  /**
   * Method that check if the input's format is : YYYY-MM-DD.
   *
   * @param input
   *          The string we want to check
   */
  public static void isDate(String input) {
    Pattern pattern = Pattern.compile("[0-9]{4}-[0-1][1-9]-[0-3][0-9]");
    Matcher matcher = pattern.matcher(input);

    if (!matcher.find()) {
      throw new ValidationException("The given input isn't a date (format YYYY-MM-DD)" + input);
    }
  }

}
