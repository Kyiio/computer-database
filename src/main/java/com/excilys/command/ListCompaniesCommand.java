package com.excilys.command;

import java.util.Scanner;

import com.excilys.model.Company;

import service.impl.CompanyServiceImpl;

public class ListCompaniesCommand extends AbstractCommand{

	public ListCompaniesCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
						
		System.out.println("Here is the list of all the company's computer :");
		
		for (Company company: CompanyServiceImpl.getInstance().listCompanies()) {
			System.out.println(company);
		}
	}
	
}
