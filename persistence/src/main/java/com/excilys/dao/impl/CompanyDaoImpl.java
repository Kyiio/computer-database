package com.excilys.dao.impl;

import com.excilys.dao.CompanyDao;
import com.excilys.model.Company;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("companyDao")
public class CompanyDaoImpl implements CompanyDao {

  private static final Logger LOGGER            = LoggerFactory.getLogger(CompanyDaoImpl.class);

  private static final String LIST_ALL_QUERY    = "SELECT company FROM Company AS company";
  private static final String GET_BY_ID_QUERY   =
      "SELECT company FROM Company AS company WHERE company.id = :id";

  private static final String GET_BY_NAME_QUERY =
      "SELECT company FROM Company AS company WHERE company.name = :name";

  private static final String DELETE_QUERY      =
      "DELETE Company AS company WHERE company.id = :id";

  @Autowired
  private SessionFactory      sessionFactory;

  @Override
  public Company getById(long id) {

    LOGGER.info("Get by id : " + id);

    Company companyResult = (Company) sessionFactory
        .getCurrentSession()
        .createQuery(GET_BY_ID_QUERY)
        .setLong("id", id)
        .uniqueResult();

    return companyResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Company> getByName(String name) {

    LOGGER.info("Get by name : " + name);

    ArrayList<Company> companyResults = (ArrayList<Company>) sessionFactory
        .getCurrentSession()

        .createQuery(GET_BY_NAME_QUERY)
        .setString("name", name)
        .list();

    return companyResults;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Company> listCompanies() {

    LOGGER.info("List all companies");

    ArrayList<Company> companyResults =
        (ArrayList<Company>) sessionFactory.getCurrentSession().createQuery(LIST_ALL_QUERY).list();

    return companyResults;
  }

  @Override
  public void deleteCompany(long id) {

    LOGGER.info("Delete company");

    sessionFactory.getCurrentSession().createQuery(DELETE_QUERY).setLong("id", id).executeUpdate();
  }
}
