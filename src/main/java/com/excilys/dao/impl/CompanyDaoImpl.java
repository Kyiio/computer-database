package com.excilys.dao.impl;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.exception.DaoException;
import com.excilys.model.Company;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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

    Company companyResult = null;
    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(GET_BY_ID_QUERY);
      query.setLong("id", id);

      companyResult = (Company) query.uniqueResult();

    } catch (HibernateException e) {
      throw new DaoException("Problem while retrieving the company with id " + id, e);
    }

    return companyResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Company> getByName(String name) {

    LOGGER.info("Get by name : " + name);

    ArrayList<Company> companyResults = null;
    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(GET_BY_NAME_QUERY);
      query.setString("name", name);

      companyResults = (ArrayList<Company>) query.list();

    } catch (HibernateException e) {
      throw new DaoException("Problem while retrieving the companies with name " + name, e);
    }

    return companyResults;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Company> listCompanies() {

    LOGGER.info("List all companies");

    ArrayList<Company> companyResults = null;
    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(LIST_ALL_QUERY);

      companyResults = (ArrayList<Company>) query.list();

    } catch (HibernateException e) {
      throw new DaoException("Problem while retrieving the list of all the companies", e);
    }

    return companyResults;
  }

  @Override
  public void deleteCompany(long id) {

    LOGGER.info("Delete company");

    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(DELETE_QUERY);
      query.setLong("id", id);
      query.executeUpdate();

    } catch (HibernateException e) {
      throw new DaoException("Problem while trying to delete the company with id : " + id, e);
    }
  }
}
