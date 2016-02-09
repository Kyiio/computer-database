package com.excilys.command;

import java.util.Scanner;

import com.excilys.DAO.CompanyDAO;
import com.excilys.DAO.DAOFactory;
import com.excilys.model.Company;

public class ListCompaniesCommand extends AbstractCommand{

	public ListCompaniesCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		CompanyDAO companyDAO = DAOFactory.getInstance().getCompanyDao();
		
		System.out.println("Here is the list of all the company's computer :");
		
		for (Company company: companyDAO.listCompanies()) {
			System.out.println(company);
		}
	}
	
}
