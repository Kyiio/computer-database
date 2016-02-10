package com.excilys.command;

import java.util.Scanner;

import service.impl.ComputerServiceImpl;

public class ListComputerCommand extends AbstractCommand{

	public ListComputerCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		System.out.println("Here is the list of all the company's computer :");
		System.out.println(ComputerServiceImpl.getInstance().listComputers());
	}
	
}
