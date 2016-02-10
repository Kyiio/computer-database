package com.excilys.command;

import java.util.Scanner;

import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.model.Computer;

public class DeleteComputerCommand extends AbstractCommand{

	public DeleteComputerCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		Computer oldComputer = askForMethodToFindComputer("delete");
		
		ComputerDAOImpl.getInstance().deleteComputer(oldComputer.getId());
		
		System.out.println("Delete complete !");
		
	}
	
}