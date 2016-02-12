package com.excilys.service;

import java.util.ArrayList;

import com.excilys.model.Company;

/**
 * 
 * @author Herbaut Bastien
 */
public interface CompanyService {
	
	/**
	 * Method that calls the getById method from the Company DAO
	 * @param id
	 * @return The company that possess that id
	 */
	public Company getById(int id);
	
	/**
	 * Method that calls the getByName method from the Company DAO
	 * @param name
	 * @return The list of all the company that matches the given name
	 */
	public ArrayList<Company> getByName(String name);
	
	/**
	 * Method that calls the listCompanies method from the Company DAO
	 * @return The list of all the company in the database
	 */
	public ArrayList<Company> listCompanies();
	
}
