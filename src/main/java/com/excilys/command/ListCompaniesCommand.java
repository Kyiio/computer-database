package com.excilys.command;

import com.excilys.model.Company;
import com.excilys.service.impl.CompanyServiceImpl;

import java.util.Scanner;

/**
 * Class that extends the AbstractCommand class and that is used in the CLI in order to retrieve and
 * print the Company List.
 * 
 * @author B. Herbaut
 */
public class ListCompaniesCommand extends AbstractCommand {

  public ListCompaniesCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    System.out.println("Here is the list of all the company :");

    for (Company company : CompanyServiceImpl.getInstance().listCompanies()) {
      System.out.println(company);
    }
  }

}
