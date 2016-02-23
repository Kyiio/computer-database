package com.excilys.service.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.impl.ComputerDaoImpl;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerService;
import com.excilys.validator.CompanyValidator;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.QueryParametersValidator;

import java.time.LocalDate;
import java.util.ArrayList;

public class ComputerServiceImpl implements ComputerService {

  public static ComputerService INSTANCE;
  public ComputerDao computerDao;

  static {
    INSTANCE = new ComputerServiceImpl();
  }

  public static ComputerService getInstance() {
    return INSTANCE;
  }

  private ComputerServiceImpl() {
    computerDao = ComputerDaoImpl.getInstance();
  }

  @Override
  public void updateComputer(Computer computer) {

    ComputerValidator.checkId(computer.getId());
    ComputerValidator.checkName(computer.getName());
    ComputerValidator.checkDateConsitency(computer.getIntroduced(), computer.getDiscontinued());

    computerDao.updateComputer(computer);
  }

  @Override
  public int insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) {

    ComputerValidator.checkName(name);
    ComputerValidator.checkDateConsitency(introduced, discontinued);

    return computerDao.insertComputer(company, introduced, discontinued, name);
  }

  @Override
  public void deleteComputer(int id) {

    ComputerValidator.checkId(id);
    computerDao.deleteComputer(id);
  }

  @Override
  public void deleteComputerAssociatedToCompany(int companyId) {

    CompanyValidator.checkId(companyId);

    computerDao.deleteComputersForCompanyId(companyId);
  }

  @Override
  public Computer getById(int id) {

    ComputerValidator.checkId(id);
    return computerDao.getById(id);
  }

  @Override
  public ArrayList<Computer> getByName(String name) {

    ComputerValidator.checkName(name);
    return computerDao.getByName(name);
  }

  @Override
  public ArrayList<Computer> listComputers() {

    return computerDao.listComputers();
  }

  @Override
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters) {

    QueryParametersValidator.validateQueryParameters(queryParameters);

    return computerDao.selectWithParameters(queryParameters);
  }

  @Override
  public int getCount(QueryParameters queryParameters) {
    return computerDao.getCount(queryParameters);
  }

}
