package com.excilys.service.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerService;
import com.excilys.validator.CompanyValidator;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.QueryParametersValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@Service("computerService")
@Transactional(readOnly = true)
public class ComputerServiceImpl implements ComputerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerServiceImpl.class);

  @Autowired
  public ComputerDao          computerDao;

  @Override
  @Transactional(readOnly = false)
  public void updateComputer(Computer computer) {

    LOGGER.info("Update computer : " + computer);

    ComputerValidator.checkComputer(computer);

    computerDao.updateComputer(computer);
  }

  @Override
  @Transactional(readOnly = false)
  public long insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) {

    LOGGER.info(new StringBuffer("Insert computer with following information : Company : ")
        .append("computer name ").append(name).append(company).append(" introduced date :")
        .append(introduced).append(" discontinued date :").append(discontinued).toString());

    ComputerValidator.checkName(name);
    ComputerValidator.checkDateConsitency(introduced, discontinued);

    long id = computerDao.insertComputer(company, introduced, discontinued, name);

    return id;
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteComputer(long id) {

    LOGGER.info("Delete computer with id : " + id);

    ComputerValidator.checkId(id);

    computerDao.deleteComputer(id);
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteComputerAssociatedToCompany(long companyId) {

    LOGGER.info("Delete computers with company id : " + companyId);

    CompanyValidator.checkId(companyId);

    computerDao.deleteComputersForCompanyId(companyId);
  }

  @Override
  public Computer getById(long id) {

    LOGGER.info("Get computer by id : " + id);

    ComputerValidator.checkId(id);

    Computer computer = computerDao.getById(id);

    return computer;
  }

  @Override
  public ArrayList<Computer> getByName(String name) {

    LOGGER.info("Get computers by name : " + name);

    ComputerValidator.checkName(name);

    ArrayList<Computer> computerList = computerDao.getByName(name);

    return computerList;
  }

  @Override
  public ArrayList<Computer> listComputers() {

    LOGGER.info("List computers");

    ArrayList<Computer> computerList = computerDao.listComputers();

    return computerList;
  }

  @Override
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters) {

    LOGGER.info("Select with parameters : " + queryParameters);

    QueryParametersValidator.validateQueryParameters(queryParameters);

    ArrayList<Computer> computerList = null;

    computerList = computerDao.selectWithParameters(queryParameters);

    return computerList;
  }

  @Override
  public long getCount(QueryParameters queryParameters) {

    LOGGER.info("Get count with parameters : " + queryParameters);

    long count = computerDao.getCount(queryParameters);

    return count;
  }

}
