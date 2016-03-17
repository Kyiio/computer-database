package com.excilys.ui;

import com.excilys.command.CommandInvoker;
import com.excilys.command.DeleteCompanyCommand;
import com.excilys.command.DeleteComputerCommand;
import com.excilys.command.InsertComputerCommand;
import com.excilys.command.ListCompaniesCommand;
import com.excilys.command.ListComputerCommand;
import com.excilys.command.UpdateComputerCommand;

import java.util.Scanner;

/**
 * This class create a CLI.
 *
 * @author Herbaut Bastien
 */
public class CommandLineInterface {

  /**
   * The Enum CommandType.
   */
  enum CommandType {

    /** The insert computer. */
    INSERT_COMPUTER,

    /** The delete computer. */
    DELETE_COMPUTER,

    /** The delete company. */
    DELETE_COMPANY,

    /** The update computer. */
    UPDATE_COMPUTER,

    /** The list computer. */
    LIST_COMPUTER,

    /** The list companies. */
    LIST_COMPANIES,

    /** The help. */
    HELP,

    /** The quit. */
    QUIT,

    /** The unknown command. */
    UNKNOWN_COMMAND
  }

  /**
   * Instantiates a new command line interface.
   */
  public CommandLineInterface() {

  }

  /**
   * This method print the CLI help on standard output.
   */
  private void showHelp() {

    System.out.println("\n####################Command List#####################\n");
    System.out.println("Type " + CommandType.INSERT_COMPUTER + " to insert computer");
    System.out.println("Type " + CommandType.DELETE_COMPUTER + " to delete computer");
    System.out.println("Type " + CommandType.UPDATE_COMPUTER + " to update computer");
    System.out.println("Type " + CommandType.LIST_COMPUTER + " to list computer");
    System.out.println(
        "Type " + CommandType.DELETE_COMPANY + " to delete a company and all associated computers");
    System.out.println("Type " + CommandType.LIST_COMPANIES + " to list companies\n");

    System.out.println("Type " + CommandType.HELP + " to review the command list !");
    System.out.println("Type " + CommandType.QUIT + " when you are done !");

  }

  /**
   * Method that starts the CLI.
   */
  public void launch() {

    System.out.println("#####################################################");
    System.out.println("############Computer Database Project CLI############");
    System.out.println("#####################################################\n");

    showHelp();

    Scanner scanner = new Scanner(System.in);

    boolean continueListenning = true;

    CommandInvoker commandInvoker = new CommandInvoker();

    // We continue to wait for input until the user types 'QUIT'
    while (continueListenning) {

      System.out.print("CDB_Project >> ");

      CommandType line;

      try {
        line = CommandType.valueOf(scanner.nextLine().trim());
      } catch (IllegalArgumentException e) {
        line = CommandType.UNKNOWN_COMMAND;
      }

      switch (line) {
        case INSERT_COMPUTER:
          commandInvoker.setCommand(new InsertComputerCommand(scanner));
          break;

        case UPDATE_COMPUTER:
          commandInvoker.setCommand(new UpdateComputerCommand(scanner));
          break;

        case DELETE_COMPUTER:
          commandInvoker.setCommand(new DeleteComputerCommand(scanner));
          break;

        case DELETE_COMPANY:
          commandInvoker.setCommand(new DeleteCompanyCommand(scanner));
          break;

        case LIST_COMPANIES:
          commandInvoker.setCommand(new ListCompaniesCommand(scanner));
          break;

        case LIST_COMPUTER:
          commandInvoker.setCommand(new ListComputerCommand(scanner));
          break;

        case HELP:
          this.showHelp();
          break;

        case QUIT:

          System.out.println("Bye !");
          continueListenning = false;
          break;

        default:

          System.out.println("Unknown command !");

          break;
      }

      commandInvoker.executeCommand();
    }
  }
}
