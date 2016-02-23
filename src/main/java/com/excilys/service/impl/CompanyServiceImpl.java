package com.excilys.service.impl;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ConnectionManager;
import com.excilys.dao.exception.DaoException;
import com.excilys.dao.impl.CompanyDaoImpl;
import com.excilys.dao.impl.ComputerDaoImpl;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.service.exception.ServiceException;
import com.excilys.validator.CompanyValidator;

import java.util.ArrayList;

public class CompanyServiceImpl implements CompanyService {

  public static CompanyService INSTANCE;
  private CompanyDao           companyDao;

  static {
    INSTANCE = new CompanyServiceImpl();
  }

  public static CompanyService getInstance() {
    return INSTANCE;
  }

  private CompanyServiceImpl() {
    companyDao = CompanyDaoImpl.getInstance();
  }

  @Override
  public Company getById(int id) {
    CompanyValidator.checkId(id);
    return companyDao.getById(id);
  }

  @Override
  public ArrayList<Company> getByName(String name) {
    CompanyValidator.checkName(name);
    return companyDao.getByName(name);
  }

  @Override
  public ArrayList<Company> listCompanies() {

    return companyDao.listCompanies();
  }

  @Override
  public void deleteCompany(int id) {

    CompanyValidator.checkId(id);

    ConnectionManager connectionManager = ConnectionManager.getInstance();

    try {

      connectionManager.startTransaction();

      // We first delete all the computers associated to the company that
      // we will delete
      ComputerDaoImpl.getInstance().deleteComputersForCompanyId(id);

      // And finally we delete the company itself
      companyDao.deleteCompany(id);

      connectionManager.endTransaction();

    } catch (DaoException e) {

      connectionManager.rollback();

      throw new ServiceException(
          "Error will trying delete the company and the associated computers ! Id : " + id, e);
    } finally {
      connectionManager.closeConnection();
    }

  }

}
