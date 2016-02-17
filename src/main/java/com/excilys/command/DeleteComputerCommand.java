package com.excilys.command;

import java.util.Scanner;

import com.excilys.command.exception.CommandException;
import com.excilys.dao.exception.DAOException;
import com.excilys.model.Computer;
import com.excilys.service.impl.ComputerServiceImpl;

public class DeleteComputerCommand extends AbstractCommand{

	public DeleteComputerCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		Computer oldComputer = askForMethodToFindComputer("delete");
		
		try {
			ComputerServiceImpl.getInstance().deleteComputer(oldComputer.getId());
			System.out.println("Delete complete !");
		} catch (DAOException e) {
			System.out.println("No matching found for the given id, delete aborted !");
		} catch (CommandException e){
			System.out.println("The id must be positive !");
		}
		
		
		
		
	}
	
}