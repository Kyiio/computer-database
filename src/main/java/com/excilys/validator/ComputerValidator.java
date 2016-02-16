package com.excilys.validator;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import com.excilys.exception.ValidationException;

public interface ComputerValidator {
	
	public static void checkId(int id){
		if(id <= 0){
			throw new ValidationException("The computer id must be positive ! Given id is " + id);
		}
	}
	
	public static void checkId(String idString){
		if(idString == null || idString.length() == 0){
			throw new ValidationException("The computer id must be an int ! Given id is " + idString);
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
			throw new ValidationException("The computer name must be set !");
		}
	}
	
	public static void checkDate(String date, String format){
		
		if(format == null || format.length() <= 0)
			format = "yyyy-MM-dd";
		
		if(date != null && date.length() > 0){
			try {
				new Timestamp(new SimpleDateFormat(format).parse(date).getTime()).toLocalDateTime();
			} catch (ParseException e) {
				throw new ValidationException("The given date " + date + " isn't matching the format : " + format);
			}
		}
	}
	
	public static void checkDateConsitency(LocalDateTime introducedDate, LocalDateTime discontinuedDate) {
		if ((discontinuedDate != null && introducedDate == null)
				|| (introducedDate != null && discontinuedDate != null && introducedDate.isAfter(discontinuedDate))) {
			throw new ValidationException("The discontinued date is before the introduced one ! introduced: " + introducedDate +  " discontinued: " + discontinuedDate);	
		}
	}
	
	public static void checkOffset(int offset){
		if(offset <= 0){
			throw new ValidationException("Negative offset: " + offset + " !");
		}
	}
	
	public static void checkPageNumber(int pageNumber){
		if(pageNumber < 0){
			throw new ValidationException("Page number can't be negative nor equals to 0 : page number : " + pageNumber);
		}
	}
}
