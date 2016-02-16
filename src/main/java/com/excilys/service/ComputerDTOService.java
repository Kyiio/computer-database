package com.excilys.service;

import java.util.ArrayList;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Computer;


/**
 * 
 * @author Bastien Herbaut
 */
public interface ComputerDTOService {

	/**
	 * Method that check the information in the computerDTO and then call the updateComputer method from ComputerService
	 * @param computer
	 * @see ComputerDTO, ComputerService
	 */
	public void updateComputer(ComputerDTO computerDTO);
	
	/**
	 * Method that check the name and dates given in parameters and then call the insertComputer method from the ComputerService
	 * @param company
	 * @param introduced
	 * @param discontinued
	 * @param name
	 * @return The id of the computer we just inserted
	 * @see ComputerDTO, ComputerService
	 */
	public int insertComputer(ComputerDTO computerDTO);
	
	/**
	 * Method that calls the deleteComputer method from the ComputerService
	 * @param id The id of the computer we want to delete
	 * @see ComputerDTO, ComputerService
	 */
	public void deleteComputer(int id);
	
	/**
	 * Method that calls the getById method from the ComputerService
	 * @param id The id of the computer we want to retrieve
	 * @return The computer DTO that matches the given id or null
	 * @see ComputerDTO, ComputerService
	 */
	public ComputerDTO getById(int id);
	
	/**
	 * Method that calls the getByName method from the ComputerService
	 * @param name The name of the computer(s) we want to retrieve
	 * @return The list of  computers DTO that matches the given name (or an empty list)
	 * @see ComputerDTO, ComputerService
	 */
	public ArrayList<ComputerDTO> getByName(String name);
	
	/**
	 * Method that calls the listComputers from the ComputerService
	 * @return The list of all the computers DTO in the database 
	 * @see ComputerDTO, ComputerService
	 */
	public ArrayList<ComputerDTO> listComputers();	
	
	/**
	 * Method that calls the getXComputersStartingAtIndexY from ComputerService.
	 * @param offset
	 * @param pageNumber
	 * @return The list of company DTO from the index pageNumber*offset to pageNumber*offset + offset 
	 * @see ComputerDTO, ComputerService
	 */
	public ArrayList<ComputerDTO> getXComputersStartingAtIndexY(int offset, int pageNumber);
}
