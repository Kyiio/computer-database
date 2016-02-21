package com.excilys.command;

import java.util.Scanner;

import com.excilys.command.exception.CommandException;
import com.excilys.dao.exception.DAOException;
import com.excilys.model.Company;
import com.excilys.service.impl.CompanyServiceImpl;

public class DeleteCompanyCommand extends AbstractCommand{

	public DeleteCompanyCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		Company company = askForExistingCompanyByAskingName();
		
		try {
			CompanyServiceImpl.getInstance().deleteCompany(company.getId());
			System.out.println("Delete complete !");
		} catch (DAOException e) {
			System.out.println("No matching found for the given id, delete aborted !");
		} catch (CommandException e){
			System.out.println("The id must be positive !");
		}
	}
}