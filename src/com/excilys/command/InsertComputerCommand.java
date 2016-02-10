package com.excilys.command;

import java.util.Scanner;

import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.model.Computer;

public class InsertComputerCommand extends AbstractCommand{

	public InsertComputerCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
	
		Computer newComputer = new Computer();
		
	    /* New name */
	    System.out.println("Please enter information about the computer");	    	    
	    newComputer.setName(askForNewComputerName());
	    	    
	    /* New introduced date */
	    newComputer.setIntroduced(askForDate("Introduced"));
	    
	    /* New discontinued date */
	    newComputer.setDiscontinued(askForDate("Discontinued"));
	    
	    /* New company associated to the computer */
	    newComputer.setCompany(askForExistingCompanyByAskingName());
	    
	    ComputerDAOImpl.getInstance().insertComputer(newComputer.getCompany(), newComputer.getIntroduced(), newComputer.getDiscontinued(), newComputer.getName());
	    
	    System.out.println("Insert done !");
	}
	
}
