package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * This class is used to Map a ResultSet into an array of other object
 * @author Bastien Herbaut 
 */
public interface ResultsMapper {

	/**
	 * This function maps the results set into an ArrayList of Company
	 * 
	 * @param resultSet The results we want to map
	 * @return The mapped company list
	 */
	public static ArrayList<Company> getCompanyList(ResultSet resultSet){
		
		ArrayList<Company> convertedResults = new ArrayList<Company>();
		
		try {
			while (resultSet.next()) {
				convertedResults.add(new Company(resultSet.getInt("ID"),
						resultSet.getString("NAME")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return convertedResults;
	}
	
	/**
	 * This function maps the results set into an ArrayList of Computer
	 * 
	 * @param resultSet The results we want to map
	 * @return The mapped computer list
	 */
	public static ArrayList<Computer> getComputerList(ResultSet resultSet){
		
		ArrayList<Computer> convertedResults = new ArrayList<Computer>();
		
		Timestamp discontinuedTimestamp;
		Timestamp introducedTimestamp;
		
		LocalDateTime discontinuedLocalDateTime;
		LocalDateTime introducedLocalDateTime;
		
		try {
			while (resultSet.next()) {
				
				discontinuedTimestamp = resultSet.getTimestamp("DISCONTINUED");
				introducedTimestamp = resultSet.getTimestamp("INTRODUCED");
				
				if(discontinuedTimestamp == null){
					discontinuedLocalDateTime = null;
				}
				else{
					discontinuedLocalDateTime = discontinuedTimestamp.toLocalDateTime();
				}
				
				if(introducedTimestamp == null){
					introducedLocalDateTime = null;
				}
				else{
					introducedLocalDateTime = introducedTimestamp.toLocalDateTime();
				}
				
				convertedResults.add(new Computer(resultSet.getInt("computer.ID"),
						new Company(resultSet.getInt("company.ID"), resultSet.getString("company.NAME")),
						resultSet.getString("computer.NAME"), discontinuedLocalDateTime, introducedLocalDateTime));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return convertedResults;
	}
	
}
