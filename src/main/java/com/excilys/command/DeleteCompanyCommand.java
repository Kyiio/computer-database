package com.excilys.command;

import com.excilys.command.exception.CommandException;
import com.excilys.dao.exception.DaoException;
import com.excilys.model.Company;

import java.util.Scanner;

public class DeleteCompanyCommand extends AbstractCommand {

  public DeleteCompanyCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    Company company = askForExistingCompanyByAskingName();

    try {
      if (company == null) {
        throw new CommandException("");
      }
      companyService.deleteCompany(company.getId());
      System.out.println("Delete complete !");
    } catch (DaoException | CommandException e) {
      System.out.println("No matching found for the given name, delete aborted !");
    }
  }
}