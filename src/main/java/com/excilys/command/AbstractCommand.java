package com.excilys.command;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.exception.ValidationException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.impl.CompanyServiceImpl;
import com.excilys.validator.InputValidator;

public abstract class AbstractCommand {

	protected Scanner scanner;

	public AbstractCommand(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public AbstractCommand() {
		
	}
	
	public abstract void execute();
	
	protected Computer askForMethodToFindComputer(String action){
		
		Computer foundComputer = null;
		String searchingMethod;
		
		while(foundComputer == null){
			System.out.println("Do you want to find the computer by 'ID' or by 'NAME' ?");
			searchingMethod = scanner.nextLine();
			
			if("ID".equals(searchingMethod))
				foundComputer = getExistingComputerByAskingId(action);
			else if("NAME".equals(searchingMethod)){
				foundComputer = getExistingComputerByAskingName(action);
			}
			else{
				System.out.println("Action not recognized !");
			}
			
			
		}
		
		return null;
	}
	
	protected Computer getExistingComputerByAskingName(String action){
		
		boolean isNameOk = false;
		ArrayList<Computer> matchingComputer = null;
		
		while(!isNameOk){
		
			System.out.println("Enter the name of the computer you want to " + action);
			
		    String nameOfExistingComputer = scanner.nextLine();
		    
		    matchingComputer = ComputerDAOImpl.getInstance().getByName(nameOfExistingComputer);
		    	    
		    if(matchingComputer.size() <= 0){
		    	System.out.println("The name you entered doesn't match any existing computer !");
		    	continue;
		    }
		    
		    isNameOk = true;
		}
	    return matchingComputer.get(0);
		
	}
	
	protected Computer getExistingComputerByAskingId(String action){
		
		boolean isIdOk = false;
		Computer matchingComputer = null;
		
		int idOfExistingComputer;
		
		while(!isIdOk){
		
			System.out.println("Enter the id of the computer you want to " + action);
			
		    try{
		    	idOfExistingComputer = scanner.nextInt();
		    }
		    catch(InputMismatchException e){
		    	System.out.println("Please !");
		    	continue;
		    }
		    
		    scanner.nextLine();
		    
		    matchingComputer = ComputerDAOImpl.getInstance().getById(idOfExistingComputer);
		    	    
		    if(matchingComputer == null){
		    	System.out.println("The id you entered doesn't match any existing computer !");
		    	continue;
		    }
		    
		    isIdOk = true;
		}
	    return matchingComputer;
		
	}
	
	protected String askForNewComputerName(){
		
	    boolean isNameOk = false;	    
	    String name = "";
	    
	    while(!isNameOk){
		    
	    	System.out.println("Computer's name :");
	    	
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
		
		Company resCompany = null;
		
		System.out.println("Company name:");
		
		String companyName = scanner.nextLine();
	    
		if(companyName.length() > 0){
			
		    ArrayList<Company> matchingCompany = CompanyServiceImpl.getInstance().getByName(companyName);
		    	    
		    if(matchingCompany.size() > 0){	
		    	resCompany = matchingCompany.get(0);
		    }
		}
		
	    return resCompany;
	}
	
	protected LocalDateTime askForDate(String dateType){
		
		String format = "yyyy-MM-dd";
		
	    System.out.println(dateType + " date (YYYY-MM-DD) :");
	    
	    String tmpDate = scanner.nextLine();
	    
	    LocalDateTime localDateTime = null;
	    
	    if(tmpDate.length() > 0){   
	    		
	    	boolean dateOk = false;
		    while (!dateOk){
		    	try {
		    		dateOk = true;

		    		InputValidator.isDate(tmpDate);
		    		localDateTime = new Timestamp(new SimpleDateFormat(format).parse(tmpDate).getTime()).toLocalDateTime();
		    		
				} catch (ParseException | ValidationException e ) {
					dateOk = false;
					System.out.println("Wrong format entered, please retry :");
					tmpDate = scanner.nextLine();
				}
		    }
	    }
	    
	    
	    return localDateTime;
	}
	
}
