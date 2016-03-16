package com.excilys.dao.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.exception.DaoException;
import com.excilys.dao.util.QueryBuilder;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository("computerDao")
public class ComputerDaoImpl implements ComputerDao {

  private static final Logger LOGGER                  =
      LoggerFactory.getLogger(ComputerDaoImpl.class);

  private static final String DELETE_QUERY            =
      "DELETE Computer AS computer WHERE computer.id = :id";

  private static final String LIST_ALL_QUERY          =
      "SELECT computer FROM Computer AS computer LEFT JOIN computer.company AS company";

  private static final String GET_BY_ID_QUERY         =
      "SELECT computer FROM Computer AS computer LEFT JOIN computer.company AS company "
          + "WHERE computer.id = :id";

  private static final String GET_BY_NAME_QUERY       =
      "SELECT computer FROM Computer AS computer LEFT JOIN computer.company AS company "
          + "WHERE computer.name = :name";

  private static final String GET_PAGE_QUERY          =
      "SELECT computer FROM Computer AS computer LEFT JOIN computer.company AS company "
          + "WHERE computer.name LIKE :searchName OR company.name LIKE :searchName";

  private static final String GET_COUNT_QUERY         =
      "SELECT COUNT(*) from Computer AS computer LEFT JOIN computer.company AS company "
          + "WHERE computer.name LIKE :searchName OR company.name LIKE :searchName";

  private static final String DELETE_WHERE_COMPANY_ID =
      "DELETE Computer AS computer WHERE computer.company_id = :companyId";

  private SessionFactory      sessionFactory;

  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public long insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) {

    LOGGER.info(new StringBuffer("Insert computer with following information : Company : ")
        .append("computer name ").append(name).append(company).append(" introduced date :")
        .append(introduced).append(" discontinued date :").append(discontinued).toString());

    if (name == null || name.length() <= 0) {
      throw new DaoException("The name of the computer must be set !");
    }

    Computer computer = new Computer();
    computer.setName(name);
    computer.setCompany(company);
    computer.setIntroduced(introduced);
    computer.setDiscontinued(discontinued);

    long id = 0;
    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();
      id = (long) session.save(computer);

    } catch (HibernateException e) {
      throw new DaoException("Problem while inserting a computer ", e);
    }

    return id;
  }

  @Override
  public void updateComputer(Computer computer) {

    LOGGER.info("Update computer " + computer);

    if (computer == null) {
      throw new DaoException("The given computer is null !");
    }

    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      session.update(computer);

    } catch (HibernateException e) {
      throw new DaoException("Problem while updating a computer ", e);
    }
  }

  @Override
  public void deleteComputer(long id) throws DaoException {

    LOGGER.info("Delete by id " + id);

    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(DELETE_QUERY);
      query.setLong("id", id);
      query.executeUpdate();

    } catch (HibernateException e) {
      throw new DaoException("Problem while trying to delete computer with id : " + id, e);
    }
  }

  @Override
  public Computer getById(long id) throws DaoException {

    LOGGER.info("Get by id : " + id);

    Computer computerResult = null;
    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(GET_BY_ID_QUERY);
      query.setLong("id", id);

      computerResult = (Computer) query.uniqueResult();

    } catch (HibernateException e) {
      throw new DaoException("Problem while retrieving the computer with id " + id, e);
    }

    return computerResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Computer> getByName(String name) {

    LOGGER.info("Get by name : " + name);

    Session session = null;
    ArrayList<Computer> computerResult = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(GET_BY_NAME_QUERY);
      query.setString("name", name);
      computerResult = (ArrayList<Computer>) query.list();

    } catch (HibernateException e) {
      throw new DaoException("Problem while retrieving the list of computers with name : " + name,
          e);
    }

    return computerResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Computer> listComputers() {

    LOGGER.info("List computers query");

    Session session = null;
    ArrayList<Computer> computerResult = null;

    try {

      session = sessionFactory.openSession();

      Query query = session.createQuery(LIST_ALL_QUERY);

      computerResult = (ArrayList<Computer>) query.list();

    } catch (HibernateException e) {
      throw new DaoException("Problem while retrieving the list of computers in the database!", e);
    }
    return computerResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters) {

    LOGGER.info("Select with parameters : " + queryParameters);

    if (queryParameters == null) {
      throw new DaoException("The queryParameters object shouldn't be null");
    }

    Session session = null;
    ArrayList<Computer> computerResult = null;

    try {
      session = sessionFactory.getCurrentSession();

      String myQuery = new QueryBuilder().append(GET_PAGE_QUERY)
          .orderBy(queryParameters.getByContent(), queryParameters.getOrder()).getQuery();

      Query query = session.createQuery(myQuery);

      query.setString("searchName", "%" + queryParameters.getSearch() + "%");
      query.setFirstResult(queryParameters.getOffset());
      query.setMaxResults(queryParameters.getPageSize());

      computerResult = (ArrayList<Computer>) query.list();

    } catch (HibernateException e) {
      throw new DaoException("Problem while executing the get page query !", e);
    }

    return computerResult;
  }

  @Override
  public long getCount(QueryParameters queryParameters) {

    LOGGER.info("Count query with parameters : " + queryParameters);

    long rowCount = 0;
    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(GET_COUNT_QUERY);
      query.setString("searchName", "%" + queryParameters.getSearch() + "%");

      rowCount = ((Long) (query.uniqueResult())).longValue();

    } catch (HibernateException e) {
      throw new DaoException("Problem while executing the get count query!", e);
    }

    return rowCount;
  }

  @Override
  public void deleteComputersForCompanyId(long companyId) {

    LOGGER.info("Delete computers with company id: " + companyId);

    Session session = null;

    try {
      session = sessionFactory.getCurrentSession();

      Query query = session.createQuery(DELETE_WHERE_COMPANY_ID);
      query.setLong("companyId", companyId);
      query.executeUpdate();

    } catch (HibernateException e) {
      throw new DaoException("Problem while deleting the computers with company id : " + companyId,
          e);
    }
  }
}
