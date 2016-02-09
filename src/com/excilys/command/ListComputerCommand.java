package com.excilys.command;

import java.util.Scanner;

import com.excilys.DAO.ComputerDAO;
import com.excilys.DAO.DAOFactory;

public class ListComputerCommand extends AbstractCommand{

	public ListComputerCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		ComputerDAO computerDAO = DAOFactory.getInstance().getComputerDao();
		
		System.out.println("Here is the list of all the company's computer :");
		System.out.println(computerDAO.listComputers());
	}
	
}
