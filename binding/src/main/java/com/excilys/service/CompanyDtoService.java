package com.excilys.service;

import com.excilys.dto.CompanyDto;

import java.util.ArrayList;

/**
 * DTO service that allows access to the DAO service and that validates the data before calling to
 * the DAO service.
 * 
 * @author B. Herbaut
 */
public interface CompanyDtoService {

  /**
   * Method that calls the getById method from the CompanyService.
   *
   * @param id the id
   * @return The company dto matching the company that possess that id.
   * @see CompanyDTO, CompanyService
   */
  public CompanyDto getById(long id);

  /**
   * Method that calls the getByName method from the Company DAO.
   *
   * @param name the name
   * @return The list of all the company dto that matches the given name.
   */
  public ArrayList<CompanyDto> getByName(String name);

  /**
   * Method that calls the listCompanies method from the Company DAO.
   * 
   * @return The list of all the company dto in the database.
   */
  public ArrayList<CompanyDto> listCompanies();

}
