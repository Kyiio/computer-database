package com.excilys.service;

import java.util.ArrayList;

import com.excilys.dto.CompanyDTO;

public interface CompanyDTOService {

	/**
	 * Method that calls the getById method from the CompanyService
	 * @param id
	 * @return The company dto matching the company that possess that id
	 * @see CompanyDTO, CompanyService
	 */
	public CompanyDTO getById(int id);
	
	/**
	 * Method that calls the getByName method from the Company DAO
	 * @param name
	 * @return The list of all the company dto that matches the given name
	 */
	public ArrayList<CompanyDTO> getByName(String name);
	
	/**
	 * Method that calls the listCompanies method from the Company DAO
	 * @return The list of all the company dto in the database
	 */
	public ArrayList<CompanyDTO> listCompanies();
	
	
}
