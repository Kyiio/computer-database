package com.excilys.service.impl;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ConnectionCloser;
import com.excilys.dao.ConnectionFactory;
import com.excilys.dao.exception.DaoException;
import com.excilys.dao.impl.CompanyDaoImpl;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.service.exception.ServiceException;
import com.excilys.validator.CompanyValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyServiceImpl implements CompanyService {

  public static CompanyService INSTANCE;
  private CompanyDao companyDao;

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
    Connection connection = null;
    try {
      connection = ConnectionFactory.getInstance().getConnection();
      connection.setAutoCommit(false);

      // We first delete all the computers associated to the company that
      // we will delete
      ComputerServiceImpl.getInstance().deleteComputerAssociatedToCompany(id, connection);

      // And finally we delete the company itself
      companyDao.deleteCompany(id, connection);

      connection.commit();

    } catch (DaoException e) {
      try {
        connection.rollback();
      } catch (SQLException e1) {
        throw new DaoException(
            "Error rollback while trying to delete the company and the associated computers !"
                + "Company id : " + id,
            e);
      }
      throw new ServiceException(
          "Error will trying delete the company and the associated computers ! Id : " + id, e);
    } catch (SQLException e) {
      throw new ServiceException(
          "Can't retrieve the connection to delete the company and the associated computers !", e);
    } finally {
      ConnectionCloser.silentClose(connection);
    }

  }

}
