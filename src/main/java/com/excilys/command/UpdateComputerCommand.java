package com.excilys.command;

import java.util.Scanner;

import com.excilys.command.exception.CommandException;
import com.excilys.model.Computer;
import com.excilys.service.impl.ComputerServiceImpl;

public class UpdateComputerCommand extends AbstractCommand{

	public UpdateComputerCommand(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void execute() {
		
		/* We ask for the name of the computer the use want to change */
					    	    		
	    Computer oldComputer = askForMethodToFindComputer("update");
	    
	    /* New name */
	    
	    System.out.println("Now enter the new informations:\n");	    	    
	    oldComputer.setName(askForNewComputerName());
	    	    
	    /* New introduced date */
	    
	    oldComputer.setIntroduced(askForDate("Introduced"));
	    
	    /* New discontinued date */
	    
	    oldComputer.setDiscontinued(askForDate("Discontinued"));
	    
	    /* New company associated to the computer */
	    		
	    oldComputer.setCompany(askForExistingCompanyByAskingName());
	    
	    try {
	    	ComputerServiceImpl.getInstance().updateComputer(oldComputer);
	    	System.out.println("Update succeeded!");
		} catch (CommandException se) {
			System.out.println("Inconsistant data entered (Issue with the name or with the dates)\nUpdate aborted!");
		}	    	    
	}
	
}