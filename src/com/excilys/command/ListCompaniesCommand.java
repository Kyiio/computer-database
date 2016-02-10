package com.excilys.command;

import java.util.Scanner;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.impl.CompanyDAOImpl;
import com.excilys.model.Company;

public class ListCompaniesCommand extends AbstractCommand{

	public ListCompaniesCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
				
		CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
		
		System.out.println("Here is the list of all the company's computer :");
		
		for (Company company: companyDAO.listCompanies()) {
			System.out.println(company);
		}
	}
	
}
