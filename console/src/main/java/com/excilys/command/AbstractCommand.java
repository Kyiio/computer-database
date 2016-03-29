package com.excilys.command;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;
import com.excilys.security.Authenticator;
import com.excilys.ui.validation.InputValidator;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Abstract class that offers some useful method for the commands of the CLI. All CLI commands must
 * extend this class.
 * 
 * @author B. Herbaut
 */
@Component
public abstract class AbstractCommand {

  protected Scanner          scanner;

  protected static Client    client;
  protected static WebTarget baseUrl;

  static {

    client = ClientBuilder.newClient().register(new Authenticator("admin", "admin"));
    baseUrl = client.target("http://localhost:6060/computerdatabase/api");
  }

  /**
   * Instantiates a new abstract command.
   *
   * @param scanner the scanner
   */
  public AbstractCommand(Scanner scanner) {

    this.scanner = scanner;
  }

  /**
   * Method that will be executed by the CLI.
   */
  public abstract void execute();

  /**
   * Close client.
   */
  public static void closeClient() {
    if (client != null) {
      client.close();
    }
  }

  /**
   * Method that asks to the user if he wants to search a computer by its name or its id.
   *
   * @param action The action that will be performed on the computer (ex : delete, update)
   * @return the computer
   */
  protected ComputerDto askForMethodToFindComputer(String action) {

    ComputerDto foundComputer = null;
    String searchingMethod;

    while (foundComputer == null) {
      System.out.println("Do you want to find the computer by 'ID' or by 'NAME'"
          + " (type 'QUIT' if you want to return to the menu)?");
      searchingMethod = scanner.nextLine().trim().toUpperCase();

      if ("ID".equals(searchingMethod)) {
        foundComputer = getExistingComputerByAskingId(action);

      } else if ("NAME".equals(searchingMethod)) {
        foundComputer = getExistingComputerByAskingName(action);

      } else if ("QUIT".equals(searchingMethod)) {
        break;

      } else {
        System.out.println("Action not recognized !");
      }
    }

    return foundComputer;
  }

  /**
   * Method that asks to the user to enter the name of the computer he wants to find. It won't stop
   * asking for a name until the user gives use a valid one or when he type 'QUIT'.
   * 
   * @param action The action that will be performed on the computer (ex : delete, update)
   * @return The first matching computer (Because multiple computers can have the same name).
   */
  protected ComputerDto getExistingComputerByAskingName(String action) {

    boolean isNameOk = false;
    ArrayList<ComputerDto> matchingComputers = null;

    while (!isNameOk) {

      System.out.println("Enter the name of the computer you want to " + action
          + " or enter 'QUIT' if you want to return to the menu");

      String nameOfExistingComputer = scanner.nextLine();

      if ("QUIT".equals(nameOfExistingComputer)) {
        return null;
      }

      matchingComputers = baseUrl
          .path("/computer/get/name")
          .queryParam("name", nameOfExistingComputer)
          .request()
          .get(new GenericType<ArrayList<ComputerDto>>() {
          });

      if (matchingComputers.size() <= 0) {
        System.out.println("The name you entered doesn't match any existing computer !");
        continue;
      }

      isNameOk = true;
    }
    return matchingComputers.get(0);

  }

  /**
   * Method that asks to the use to enter the id of the computer he wants to find. It won't stop
   * asking for an id until the user gives us a valid one.
   * 
   * @param action The action that will be performed on the computer (ex : delete, update)
   * @return The matching computer.
   */
  protected ComputerDto getExistingComputerByAskingId(String action) {

    boolean isIdOk = false;
    ComputerDto matchingComputer = null;

    int idOfExistingComputer;

    while (!isIdOk) {

      System.out.println("Enter the id of the computer you want to " + action);

      try {
        idOfExistingComputer = scanner.nextInt();

      } catch (InputMismatchException e) {
        System.out.println("Enter a valid id or -1 if you want to quit !");
        scanner.nextLine();
        continue;
      }

      scanner.nextLine();

      if (idOfExistingComputer == -1) {
        break;
      }

      matchingComputer =
          baseUrl.path("/computer/get/id").queryParam("id", idOfExistingComputer).request().get(
              ComputerDto.class);

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
    String name = null;

    while (!isNameOk) {

      System.out.println("Computer's name :");

      name = scanner.nextLine();

      if ("QUIT".equals(name)) {
        break;
      }

      if (name.length() <= 0) {
        System.out.println(
            "The name of the computer must be set (You can return to the menu typing 'QUIT')!");
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
  protected CompanyDto askForExistingCompanyByAskingName() {

    CompanyDto resCompany = null;
    String companyName = null;
    boolean companyOk = false;

    while (!companyOk) {

      System.out
          .println("Enter the company's name (or 'PASS' if you don't wan't to enter anything):");

      companyName = scanner.nextLine().trim();

      if ("PASS".equals(companyName.toUpperCase())) {
        break;
      }

      if (companyName.length() > 0) {

        ArrayList<CompanyDto> matchingCompanies =
            baseUrl.path("/company/get/name").queryParam("name", companyName).request().get(
                new GenericType<ArrayList<CompanyDto>>() {
                });

        if (matchingCompanies.size() > 0) {
          resCompany = matchingCompanies.get(0);
          companyOk = true;

        } else {
          System.out.println("No company found for the given name");
        }
      }
    }

    return resCompany;
  }

  /**
   * Method that asks to the user to enter a date that respect the following format yyyy-MM-dd or an
   * empty one.
   * 
   * @param dateType The format that the date has to match
   * @return The LocalDateTime object created from the user input.
   */
  protected String askForDate(String dateType) {

    String format = "MM-dd-yyyy";

    System.out.println(dateType + " date (MM-DD-YYYY) type 'PASS' if you want to skip:");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

    String tmpDate;
    String dateStr = null;
    boolean dateOk = false;

    while (!dateOk) {
      try {
        dateOk = true;
        tmpDate = scanner.nextLine().trim();

        if ("PASS".equals(tmpDate.toUpperCase())) {
          break;
        }

        InputValidator.isDate(tmpDate);
        dateStr = new Timestamp(new SimpleDateFormat(format).parse(tmpDate).getTime())
            .toLocalDateTime()
            .format(formatter);

      } catch (ParseException | IllegalArgumentException e) {
        dateOk = false;
        System.out.println("Wrong format entered, please retry :");
        tmpDate = scanner.nextLine();
      }
    }

    return dateStr;
  }
}
