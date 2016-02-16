package com.excilys.validator;

import com.excilys.exception.ValidationException;

public class CompanyValidator {

	public static void checkId(int id){
		if(id <= 0){
			throw new ValidationException("The company id must be positive ! Given id is " + id);
		}
	}
	
	public static void checkId(String idString){
		if(idString == null || idString.length() == 0){
			throw new ValidationException("The company id must be an int ! Given id is " + idString);
		}
		
		int id = 0;
		try{
			id = Integer.parseInt(idString);
		}
		catch(Exception e){
			throw new ValidationException("Error parsing the given id ! Given id is " + idString);
		}
		
		checkId(id);
	}
	
	public static void checkName(String name){
		if (name == null || name.length() <= 0) {
			throw new ValidationException("The company name must be set !");
		}
	}
	
	
}
