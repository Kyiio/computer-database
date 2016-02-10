package com.excilys.command;

import java.util.Scanner;

import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.model.Computer;

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
	    
	    ComputerDAOImpl.getInstance().updateComputer(oldComputer);
	    
	    System.out.println("Update done !");
	    	    
	}
	
}