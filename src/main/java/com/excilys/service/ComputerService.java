package com.excilys.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * 
 * @author Herbaut Bastien
 */
public interface ComputerService {

	/**
	 * Method that check the information in the computer and then call the updateComputer method from the Computer DAO
	 * @param computer
	 * @see ComputerDAO
	 */
	public void updateComputer(Computer computer);
	
	/**
	 * Method that check the name and dates given in parameters and then call the insertComputer method from the Computer DAO
	 * @param company
	 * @param introduced
	 * @param discontinued
	 * @param name
	 * @return The id of the computer we just inserted
	 * @see ComputerDAO
	 */
	public int insertComputer(Company company, LocalDateTime introduced, LocalDateTime discontinued, String name);
	
	/**
	 * Method that calls the deleteComputer method from the Computer DAO
	 * @param id The id of the computer we want to delete
	 * @see ComputerDAO
	 */
	public void deleteComputer(int id);
	
	/**
	 * Method that calls the getById method from the Computer DAO
	 * @param id The id of the computer we want to retrieve
	 * @return The computer that matches the given id or null
	 * @see ComputerDAO
	 */
	public Computer getById(int id);
	
	/**
	 * Method that calls the getByName method from the Computer DAO
	 * @param name The name of the computer(s) we want to retrieve
	 * @return The list of computers that matches the given name (or an empty list)
	 * @see ComputerDAO
	 */
	public ArrayList<Computer> getByName(String name);
	
	/**
	 * Method that calls the listComputers from the Computer DAO
	 * @return The list of all the computers in the database 
	 * @see ComputerDAO
	 */
	public ArrayList<Computer> listComputers();
	
	/**
	 * Method that calls the getXComputersStartingAtIndexY from Computer DAO
	 * @param offset
	 * @param pageNumber
	 * @return The list of company from the index pageNumber*offset to pageNumber*offset + offset
	 * @see ComputerDAO
	 */
	public ArrayList<Computer> getXComputersStartingAtIndexY(int offset, int pageNumber);
	
	/**
	 * Method that calls the getNbComputer from the ComputerDAO
	 * @return The number of computer in the database
	 * @see ComputerDAO
	 */
	public int getCount();
}
