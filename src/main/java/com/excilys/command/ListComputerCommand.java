package com.excilys.command;

import com.excilys.service.impl.ComputerServiceImpl;

import java.util.Scanner;

/**
 * Class that extends the AbstractCommand class and that is used in the CLI in order to retrieve and
 * print the Computer List.
 * 
 * @author B. Herbaut
 */
public class ListComputerCommand extends AbstractCommand {

  public ListComputerCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    System.out.println("Here is the list of all the company's computer :");
    System.out.println(ComputerServiceImpl.getInstance().listComputers());
  }

}
