package com.excilys.service.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerService;
import com.excilys.validator.CompanyValidator;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.QueryParametersValidator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service("computerService")
public class ComputerServiceImpl implements ComputerService {

  @Autowired
  public ComputerDao     computerDao;
  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public void updateComputer(Computer computer) {

    ComputerValidator.checkId(computer.getId());
    ComputerValidator.checkName(computer.getName());
    ComputerValidator.checkDateConsitency(computer.getIntroduced(), computer.getDiscontinued());

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    computerDao.updateComputer(computer);

    session.close();
  }

  @Override
  public long insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) {

    ComputerValidator.checkName(name);
    ComputerValidator.checkDateConsitency(introduced, discontinued);

    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();

    long id = computerDao.insertComputer(company, introduced, discontinued, name);

    transaction.commit();
    session.close();

    return id;
  }

  @Override
  public void deleteComputer(long id) {

    ComputerValidator.checkId(id);

    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();

    computerDao.deleteComputer(id);

    transaction.commit();
    session.close();
  }

  @Override
  public void deleteComputerAssociatedToCompany(long companyId) {

    CompanyValidator.checkId(companyId);

    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();

    computerDao.deleteComputersForCompanyId(companyId);

    transaction.commit();
    session.close();
  }

  @Override
  public Computer getById(long id) {

    ComputerValidator.checkId(id);

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Computer computer = computerDao.getById(id);

    session.close();

    return computer;
  }

  @Override
  public ArrayList<Computer> getByName(String name) {

    ComputerValidator.checkName(name);

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    ArrayList<Computer> computerList = computerDao.getByName(name);

    session.close();

    return computerList;
  }

  @Override
  public ArrayList<Computer> listComputers() {

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    ArrayList<Computer> computerList = computerDao.listComputers();

    session.close();

    return computerList;
  }

  @Override
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters) {

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    QueryParametersValidator.validateQueryParameters(queryParameters);
    ArrayList<Computer> computerList = computerDao.selectWithParameters(queryParameters);

    session.close();

    return computerList;
  }

  @Override
  public long getCount(QueryParameters queryParameters) {

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    long count = computerDao.getCount(queryParameters);

    session.close();

    return count;
  }

}
