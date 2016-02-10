package com.excilys.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.exception.DAOException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * This DAO offers different methods to access and modify the Computer table from the database  
 * @author Bastien Herbaut
 */
public abstract class ComputerDAO{
	
	/**
	 * Method to create and insert a new computer in the database
	 * 
	 * @param company The company that owns the computer
	 * @param discontinued The discontinued date
	 * @param introduced The introduced date
	 * @param name The name of the computer
	 * @return The id set by the database for the new computer
	 * 
	 * @see Company
	 */
	public abstract int insertComputer(Company company, LocalDateTime discontinued, LocalDateTime introduced, String name);
	
	/**
	 * Method that save the changes made to the computer given in parameter into the database.
	 * Before storing the computer, we check that the information it contains are consistent
	 * @param computer The computer we want to update
	 * @see Computer
	 */
	public abstract void updateComputer(Computer computer);
	
	/**
	 * Method that delete the computer that possess the id given in parameter
	 * @param id The id of the computer we want to delete
	 */
	public abstract void deleteComputer(int id);
	
	/**
	 * This method access the database to retrieve the computer with the given id.
	 * If the id doesn't match any existing id, null is returned
	 * @param id The id of the computer we want to retrieve
	 * @return The matching computer or null
	 * @see Computer
	 */
	public abstract Computer getById(int id);
	
	/**
	 * This method retrieve a list of all the computer that possess the name given.
	 * If none are found, an empty list is returned
	 * @param name
	 * @return The list of matching computers
	 */
	public abstract ArrayList<Computer> getByName(String name);
	
	/**
	 * This method retrieves all the computer in the database and store them into a list
	 * @return The list of all the computers in the database
	 */
	public abstract ArrayList<Computer> listComputers();
	
	/**
	 * Method that checks if the data are correct
	 * @param name The computer name we want to check
	 * @param introduced Introduced date for the computer (should be before the discontinued)
	 * @param discontinued Discontinued date for the computer (should be after the introduced)
	 * @throws DAOException
	 */
	public void checkData(String name, LocalDateTime introduced, LocalDateTime discontinued) throws DAOException{
		
		if(name == null || name.length() == 0){
			throw new DAOException("Computer's name must be set !");
		}
		
		if((introduced == null && discontinued != null) || (introduced != null && discontinued != null && introduced.isAfter(discontinued))){
			throw new DAOException("Discontinued date should be set after Introduced date ! Discontinued :" + discontinued + " Introduced :" + introduced);
		}
		
	}
	
	/**
	 * Method that checks if the data are correct
	 * @param computer The computer that contains the data we have to check
	 * @throws DAOException
	 */
	public void checkData(Computer computer) throws DAOException{
		
		checkData(computer.getName(), computer.getIntroduced(), computer.getDiscontinued());
		
	}

}
