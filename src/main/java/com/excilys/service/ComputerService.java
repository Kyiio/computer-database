package com.excilys.service;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;

/**
 * DAO Service that allows access to the database.
 * 
 * @author B. Herbaut
 */
public interface ComputerService {

	/**
	 * Method that check the information in the computer and then call the
	 * updateComputer method from {@link ComputerDAO}
	 * 
	 * @param computer
	 */
	public void updateComputer(Computer computer);

	/**
	 * Method that check the name and dates given in parameters and then call
	 * the insertComputer method from {@link ComputerDAO}
	 * 
	 * @param company
	 * @param introduced
	 * @param discontinued
	 * @param name
	 * @return The id of the computer we just inserted
	 */
	public int insertComputer(Company company, LocalDate introduced, LocalDate discontinued, String name);

	/**
	 * Method that calls the deleteComputer method from {@link ComputerDAO}
	 * 
	 * @param id
	 *            The id of the computer we want to delete
	 */
	public void deleteComputer(int id);

	/**
	 * Method that calls the deleteComputersForCompanyId method from
	 * {@link ComputerDAO}
	 * 
	 * @param companyId
	 *            The id of the company for witch we have to delete the computers
	 * @param connection
	 *            The connection to the database
	 */
	public void deleteComputerAssociatedToCompany(int companyId, Connection connection);

	/**
	 * Method that calls the getById method from the {@link ComputerDAO}
	 * 
	 * @param id
	 *            The id of the computer we want to retrieve
	 * @return The computer that matches the given id or null
	 */
	public Computer getById(int id);

	/**
	 * Method that calls the getByName method from the {@link ComputerDAO}
	 * 
	 * @param name
	 *            The name of the computer(s) we want to retrieve
	 * @return The list of computers that matches the given name (or an empty
	 *         list)
	 */
	public ArrayList<Computer> getByName(String name);

	/**
	 * Method that calls the listComputers from the {@link ComputerDAO}
	 * 
	 * @return The list of all the computers in the database
	 */
	public ArrayList<Computer> listComputers();

	/**
	 * Method that calls the selectWithParameters from {@link ComputerDAO}
	 * 
	 * @param queryParameters
	 * 
	 * @return The list of company from the index pageNumber*offset to
	 *         pageNumber*offset + offset
	 */
	public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters);

	/**
	 * Method that calls the getNbComputer from the {@link ComputerDAO}
	 * 
	 * @param queryParameters
	 * 
	 * @return The number of computer in the database
	 */
	public int getCount(QueryParameters queryParameters);
}
