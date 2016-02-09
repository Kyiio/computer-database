package com.excilys.command;

import java.util.Scanner;

import com.excilys.DAO.DAOFactory;
import com.excilys.model.Computer;

public class DeleteComputerCommand extends AbstractCommand{

	public DeleteComputerCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		Computer oldComputer = getExistingComputerByAskingName("Enter the name of the computer you want to delete:");
		
		DAOFactory.getInstance().getComputerDao().deleteComputer(oldComputer.getId());
		
		System.out.println("Delete complete !");
		
	}
	
}