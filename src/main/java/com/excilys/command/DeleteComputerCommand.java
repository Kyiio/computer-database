package com.excilys.command;

import java.util.Scanner;

import com.excilys.model.Computer;

import service.impl.ComputerServiceImpl;

public class DeleteComputerCommand extends AbstractCommand{

	public DeleteComputerCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		Computer oldComputer = askForMethodToFindComputer("delete");
		
		ComputerServiceImpl.getInstance().deleteComputer(oldComputer.getId());
		
		System.out.println("Delete complete !");
		
	}
	
}