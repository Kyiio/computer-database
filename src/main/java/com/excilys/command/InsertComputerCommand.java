package com.excilys.command;

import com.excilys.command.exception.CommandException;
import com.excilys.model.Computer;

import java.util.Scanner;

/**
 * Class that extends the AbstractCommand class and that is used in the CLI in order to handle the
 * insertion of a computer in the database by the user.
 * 
 * @author B. Herbaut
 */
public class InsertComputerCommand extends AbstractCommand {

  public InsertComputerCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    Computer newComputer = new Computer();

    /* New name */
    System.out.println("Please enter information about the computer");
    newComputer.setName(askForNewComputerName());

    /* New introduced date */
    newComputer.setIntroduced(askForDate("Introduced"));

    /* New discontinued date */
    newComputer.setDiscontinued(askForDate("Discontinued"));

    /* New company associated to the computer */
    newComputer.setCompany(askForExistingCompanyByAskingName());

    try {
      computerService.insertComputer(newComputer.getCompany(), newComputer.getIntroduced(),
          newComputer.getDiscontinued(), newComputer.getName());
      System.out.println("Insert done !");
    } catch (CommandException se) {
      System.out.println(
          "Inconsistant data entered (Issue with the name or with the dates)\nInsert aborted!");
    }

  }

}
