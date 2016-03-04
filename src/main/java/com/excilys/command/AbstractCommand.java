package com.excilys.command;

import com.excilys.exception.ValidationException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.InputValidator;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Abstract class that offers some useful method for the commands of the CLI. All CLI commands must
 * extend this class.
 * 
 * @author B. Herbaut
 */
@Component
public abstract class AbstractCommand {

  protected Scanner scanner;

  protected ComputerService computerService;
  protected CompanyService  companyService;

  /**
   * Instantiates a new abstract command.
   *
   * @param scanner the scanner
   */
  public AbstractCommand(Scanner scanner) {
    this.scanner = scanner;

    try (ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("applicationContext.xml")) {

      computerService = applicationContext.getBean("computerService", ComputerService.class);
      companyService = applicationContext.getBean("companyService", CompanyService.class);
    }
  }

  /**
   * Method that will be executed by the CLI.
   */
  public abstract void execute();

  /**
   * Method that asks to the user if he wants to search a computer by its name or its id.
   *
   * @param action
   *          The action that will be performed on the computer (ex : delete, update)
   * @return the computer
   */
  protected Computer askForMethodToFindComputer(String action) {

    Computer foundComputer = null;
    String searchingMethod;

    while (foundComputer == null) {
      System.out.println("Do you want to find the computer by 'ID' or by 'NAME' ?");
      searchingMethod = scanner.nextLine();

      if ("ID".equals(searchingMethod)) {
        foundComputer = getExistingComputerByAskingId(action);
      } else if ("NAME".equals(searchingMethod)) {
        foundComputer = getExistingComputerByAskingName(action);
      } else {
        System.out.println("Action not recognized !");
      }

    }

    return null;
  }

  /**
   * Method that asks to the user to enter the name of the computer he wants to find. It won't stop
   * asking for a name until the user gives use a valid one.
   * 
   * @param action
   *          The action that will be performed on the computer (ex : delete, update)
   * @return The first matching computer (Because multiple computers can have the same name).
   */
  protected Computer getExistingComputerByAskingName(String action) {

    boolean isNameOk = false;
    ArrayList<Computer> matchingComputer = null;

    while (!isNameOk) {

      System.out.println("Enter the name of the computer you want to " + action);

      String nameOfExistingComputer = scanner.nextLine();

      matchingComputer = computerService.getByName(nameOfExistingComputer);

      if (matchingComputer.size() <= 0) {
        System.out.println("The name you entered doesn't match any existing computer !");
        continue;
      }

      isNameOk = true;
    }
    return matchingComputer.get(0);

  }

  /**
   * Method that asks to the use to enter the id of the computer he wants to find. It won't stop
   * asking for an id until the user gives us a valid one.
   * 
   * @param action
   *          The action that will be performed on the computer (ex : delete, update)
   * @return The matching computer.
   */
  protected Computer getExistingComputerByAskingId(String action) {

    boolean isIdOk = false;
    Computer matchingComputer = null;

    int idOfExistingComputer;

    while (!isIdOk) {

      System.out.println("Enter the id of the computer you want to " + action);

      try {
        idOfExistingComputer = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Please !");
        continue;
      }

      scanner.nextLine();

      matchingComputer = computerService.getById(idOfExistingComputer);

      if (matchingComputer == null) {
        System.out.println("The id you entered doesn't match any existing computer !");
        continue;
      }

      isIdOk = true;
    }
    return matchingComputer;
  }

  /**
   * Method that ask to the user to enter a name for the new computer he wants to create or update.
   * It won't stop asking a name to the user until he gives use a non empty one.
   * 
   * @return The name given by the user.
   */
  protected String askForNewComputerName() {

    boolean isNameOk = false;
    String name = "";

    while (!isNameOk) {

      System.out.println("Computer's name :");

      name = scanner.nextLine();

      if (name.length() <= 0) {
        System.out.println("The name of the computer must be set !");
        continue;
      }

      isNameOk = true;
    }

    return name;
  }

  /**
   * Method that asks to the user to enter an existing company name or an empty one.
   * 
   * @return The Company object that we retrieved from the database.
   */
  protected Company askForExistingCompanyByAskingName() {

    Company resCompany = null;

    System.out.println("Company name:");

    String companyName = scanner.nextLine();

    if (companyName.length() > 0) {

      ArrayList<Company> matchingCompany = companyService.getByName(companyName);

      if (matchingCompany.size() > 0) {
        resCompany = matchingCompany.get(0);
      }
    }

    return resCompany;
  }

  /**
   * Method that asks to the user to enter a date that respect the following format yyyy-MM-dd or an
   * empty one.
   * 
   * @param dateType
   *          The format that the date has to match
   * @return The LocalDateTime object created from the user input.
   */
  protected LocalDate askForDate(String dateType) {

    String format = "yyyy-MM-dd";

    System.out.println(dateType + " date (YYYY-MM-DD) :");

    String tmpDate = scanner.nextLine();

    LocalDate localDate = null;

    if (tmpDate.length() > 0) {

      boolean dateOk = false;
      while (!dateOk) {
        try {
          dateOk = true;

          InputValidator.isDate(tmpDate);
          localDate = new Timestamp(new SimpleDateFormat(format).parse(tmpDate).getTime())
              .toLocalDateTime().toLocalDate();

        } catch (ParseException | ValidationException e) {
          dateOk = false;
          System.out.println("Wrong format entered, please retry :");
          tmpDate = scanner.nextLine();
        }
      }
    }

    return localDate;
  }

}
