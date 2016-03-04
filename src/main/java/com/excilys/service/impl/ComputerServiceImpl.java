package com.excilys.service.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerService;
import com.excilys.validator.CompanyValidator;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.QueryParametersValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service("computerService")
public class ComputerServiceImpl implements ComputerService {

  @Autowired
  public ComputerDao computerDao;

  @Override
  public void updateComputer(Computer computer) {

    ComputerValidator.checkId(computer.getId());
    ComputerValidator.checkName(computer.getName());
    ComputerValidator.checkDateConsitency(computer.getIntroduced(), computer.getDiscontinued());

    computerDao.updateComputer(computer);
  }

  @Override
  public long insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) {

    ComputerValidator.checkName(name);
    ComputerValidator.checkDateConsitency(introduced, discontinued);

    return computerDao.insertComputer(company, introduced, discontinued, name);
  }

  @Override
  public void deleteComputer(long id) {

    ComputerValidator.checkId(id);
    computerDao.deleteComputer(id);
  }

  @Override
  public void deleteComputerAssociatedToCompany(long companyId) {

    CompanyValidator.checkId(companyId);

    computerDao.deleteComputersForCompanyId(companyId);
  }

  @Override
  public Computer getById(long id) {

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
  public long getCount(QueryParameters queryParameters) {
    return computerDao.getCount(queryParameters);
  }

}
