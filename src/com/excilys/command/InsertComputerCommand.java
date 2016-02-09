package com.excilys.command;

import java.util.Scanner;

import com.excilys.DAO.DAOFactory;
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
	    newComputer.setDiscontinued(askForDate("Introduced"));
	    
	    /* New discontinued date */
	    newComputer.setDiscontinued(askForDate("Discontinued"));
	    
	    /* New company associated to the computer */
	    newComputer.setCompany(askForExistingCompanyByAskingName());
	    
	    DAOFactory.getInstance().getComputerDao().insertComputer(newComputer.getCompany(), newComputer.getDiscontinued(), newComputer.getIntroduced(), newComputer.getName());
	    
	    System.out.println("Insert done !");
	}
	
}
