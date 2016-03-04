package com.excilys.service;

import com.excilys.dto.ComputerDto;
import com.excilys.model.QueryParameters;

import java.util.ArrayList;

/**
 * DTO service that allows access to the DAO service and that validates the data before calling to
 * the DAO service.
 * 
 * @author B. Herbaut
 */
public interface ComputerDtoService {

  /**
   * Method that check the information in the computerDTO and then call the updateComputer method
   * from ComputerService.
   *
   * @param computerDto the computer dto
   * @see ComputerDTO, ComputerService
   */
  public void updateComputer(ComputerDto computerDto);

  /**
   * Method that check the name and dates given in parameters and then call the insertComputer
   * method from the ComputerService.
   *
   * @param computerDto the computer dto
   * @return The id of the computer we just inserted.
   * @see ComputerDTO, ComputerService
   */
  public long insertComputer(ComputerDto computerDto);

  /**
   * Method that calls the deleteComputer method from the ComputerService.
   * 
   * @param id The id of the computer we want to delete.
   * @see ComputerDTO, ComputerService
   */
  public void deleteComputer(long id);

  /**
   * Method that calls the getById method from the ComputerService.
   * 
   * @param id The id of the computer we want to retrieve
   * @return The computer DTO that matches the given id or null
   * @see ComputerDTO, ComputerService
   */
  public ComputerDto getById(long id);

  /**
   * Method that calls the getByName method from the ComputerService.
   * 
   * @param name The name of the computer(s) we want to retrieve.
   * @return The list of computers DTO that matches the given name (or an empty list).
   * @see ComputerDTO, ComputerService
   */
  public ArrayList<ComputerDto> getByName(String name);

  /**
   * Method that calls the listComputers from the ComputerService.
   * 
   * @return The list of all the computers DTO in the database.
   * @see ComputerDTO, ComputerService
   */
  public ArrayList<ComputerDto> listComputers();

  /**
   * Method that calls the selectWithParameters from ComputerService.
   *
   * @param queryParameters the query parameters
   * @return The computerDTO list that match the criteria
   * @see ComputerDTO, ComputerService
   */
  public ArrayList<ComputerDto> selectWithParameters(QueryParameters queryParameters);

  /**
   * Method that calls the getNbComputer from the ComputerService.
   * 
   * @param queryParameters
   * @return The number of computer in the database.
   * @see ComputerService
   */
  public long getCount(QueryParameters queryParameters);
}
