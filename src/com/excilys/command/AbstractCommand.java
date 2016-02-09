package com.excilys.command;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.DAO.DAOFactory;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public abstract class AbstractCommand {

	protected Scanner scanner; 
	
	public AbstractCommand(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public AbstractCommand() {
		
	}
	
	public abstract void execute();
	
	protected Computer getExistingComputerByAskingName(String prompt){
		
		boolean isNameOk = false;
		ArrayList<Computer> matchingComputer = null;
		
		while(!isNameOk){
		
			System.out.println(prompt);
			//scanner.nextLine();
		    String nameOfExistingComputer = scanner.nextLine();
		    
		    matchingComputer = DAOFactory.getInstance().getComputerDao().getByName(nameOfExistingComputer);
		    	    
		    if(matchingComputer.size() <= 0){
		    	System.out.println("The name you entered doesn't match any existing computer !");
		    	continue;
		    }
		    
		    isNameOk = true;
		}
	    return matchingComputer.get(0);
		
	}
	
	protected String askForNewComputerName(){
		
	    boolean isNameOk = false;	    
	    String name = "";
	    
	    while(!isNameOk){
		    
	    	System.out.println("Computer's name :");
	    	//scanner.nextLine();
	    	name = scanner.nextLine();
		    
		    if(name.length() <= 0){
		    	System.out.println("The name of the computer must be set !");
		    	continue;
		    }
		    
		    isNameOk = true;
	    }
	    	    
	    return name;
	}
		
	protected Company askForExistingCompanyByAskingName(){
		
		System.out.println("Company name:");
		//scanner.nextLine();
	    
		String companyName = scanner.nextLine();
	    
	    ArrayList<Company> matchingCompany = DAOFactory.getInstance().getCompanyDao().getByName(companyName);
	    	    
	    if(matchingCompany.size() > 0){	
	    	return matchingCompany.get(0);
	    }
	    
	    return null;
	}
	
	protected Timestamp askForDate(String dateType){
		
		String format = "yyyy-MM-dd";
		
	    System.out.println(dateType + " date (YYYY-MM-DD) :");
	    //scanner.nextLine();
	    
	    String tmpDate = scanner.nextLine();
	    
	    Timestamp timestamp = null;
	    
	    if(tmpDate.length() > 0){   
	    		
		    try {
		    	timestamp = new Timestamp(new SimpleDateFormat(format).parse(tmpDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    }
	    
	    return timestamp;
	}
	
}
