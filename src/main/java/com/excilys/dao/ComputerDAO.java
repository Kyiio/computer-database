package com.excilys.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.dao.exception.DAOException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * This DAO offers different methods to access and modify the Computer table
 * from the database
 * 
 * @author B. Herbaut
 */
public interface ComputerDAO {

	/**
	 * Method to insert a new computer in the database.
	 * 
	 * @param company
	 *            The company that owns the computer.
	 * @param discontinued
	 *            The discontinued date.
	 * @param introduced
	 *            The introduced date.
	 * @param name
	 *            The name of the computer.
	 * @return The id set by the database for the new computer.
	 * @throws DAOException
	 * 
	 * @see Company
	 */
	public int insertComputer(Company company, LocalDateTime discontinued, LocalDateTime introduced, String name)
			throws DAOException;

	/**
	 * Method that save the changes made to the computer given in parameter into
	 * the database.
	 * 
	 * @param computer
	 *            The computer we want to update.
	 * @throws DAOException
	 * @see Computer
	 */
	public void updateComputer(Computer computer) throws DAOException;

	/**
	 * Method that delete the computer that possess the id given in parameter.
	 * 
	 * @param id
	 *            The id of the computer we want to delete.
	 * @throws DAOException
	 */
	public void deleteComputer(int id) throws DAOException;

	/**
	 * This method access the database to retrieve the computer with the given
	 * id.
	 * 
	 * If the id doesn't match any existing id, null is returned.
	 * 
	 * @param id
	 *            The id of the computer we want to retrieve.
	 * @return The matching computer or null.
	 * @throws DAOException
	 * @see Computer
	 */
	public Computer getById(int id) throws DAOException;

	/**
	 * This method retrieve a list of all the computer that possess the name
	 * given.
	 * 
	 * If none are found, an empty list is returned.
	 * 
	 * @param name
	 *            The name of the computer(s).
	 * @return The list of matching computers.
	 * @throws DAOException
	 */
	public ArrayList<Computer> getByName(String name) throws DAOException;

	/**
	 * This method retrieves all the computer in the database and store them
	 * into a list.
	 * 
	 * @return The list of all the computers in the database.
	 * @throws DAOException
	 */
	public ArrayList<Computer> listComputers() throws DAOException;

	/**
	 * This method retrieves {@code offset} from the database starting at the
	 * index {@code pageNumber * offset}
	 * 
	 * @param offset
	 * @param pageNumber
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<Computer> getXComputersStartingAtIndexY(int offset, int pageNumber) throws DAOException;

	/**
	 * @return The number of Computer that are in the database.
	 * @throws DAOException
	 */
	public int getCount() throws DAOException;
}
