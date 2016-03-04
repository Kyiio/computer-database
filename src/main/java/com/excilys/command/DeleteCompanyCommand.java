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
      companyService.deleteCompany(company.getId());
      System.out.println("Delete complete !");
    } catch (DaoException e) {
      System.out.println("No matching found for the given id, delete aborted !");
    } catch (CommandException e) {
      System.out.println("The id must be positive !");
    }
  }
}