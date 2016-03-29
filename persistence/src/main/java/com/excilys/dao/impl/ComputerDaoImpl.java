package com.excilys.dao.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.exception.DaoException;
import com.excilys.dao.util.QueryBuilder;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;

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

  private static final String LIST_ALL_QUERY          = "SELECT computer FROM Computer AS computer";

  private static final String GET_BY_ID_QUERY         =
      "SELECT computer FROM Computer AS computer WHERE computer.id = :id";

  private static final String GET_BY_NAME_QUERY       =
      "SELECT computer FROM Computer AS computer WHERE computer.name = :name";

  private static final String GET_PAGE_QUERY          =
      "SELECT computer FROM Computer AS computer LEFT JOIN computer.company AS company "
          + "WHERE computer.name LIKE :searchName OR company.name LIKE :searchName";

  private static final String GET_COUNT_QUERY         =
      "SELECT COUNT(*) FROM Computer AS computer LEFT JOIN computer.company AS company "
          + "WHERE computer.name LIKE :searchName OR company.name LIKE :searchName";

  private static final String DELETE_WHERE_COMPANY_ID =
      "DELETE Computer AS computer WHERE computer.company.id = :companyId";

  private SessionFactory      sessionFactory;

  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public long insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) {

    LOGGER.info(new StringBuilder("Insert computer with following information : Company : ")
        .append("computer name ")
        .append(name)
        .append(company)
        .append(" introduced date :")
        .append(introduced)
        .append(" discontinued date :")
        .append(discontinued)
        .toString());

    if (name == null || name.length() <= 0) {
      throw new DaoException("The name of the computer must be set !");
    }

    Computer computer = new Computer(0, company, name, discontinued, introduced);

    Session session = sessionFactory.getCurrentSession();
    long id = (long) session.save(computer);

    return id;
  }

  @Override
  public void updateComputer(Computer computer) {

    LOGGER.info("Update computer " + computer);

    if (computer == null) {
      throw new DaoException("The given computer is null !");
    }

    sessionFactory.getCurrentSession().update(computer);
  }

  @Override
  public void deleteComputer(long id) throws DaoException {

    LOGGER.info("Delete by id " + id);

    sessionFactory.getCurrentSession().createQuery(DELETE_QUERY).setLong("id", id).executeUpdate();

  }

  @Override
  public Computer getById(long id) {

    LOGGER.info("Get by id : " + id);

    Computer computerResult = (Computer) sessionFactory
        .getCurrentSession()
        .createQuery(GET_BY_ID_QUERY)
        .setLong("id", id)
        .uniqueResult();

    return computerResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Computer> getByName(String name) {

    LOGGER.info("Get by name : " + name);

    ArrayList<Computer> computerResult = (ArrayList<Computer>) sessionFactory
        .getCurrentSession()
        .createQuery(GET_BY_NAME_QUERY)
        .setString("name", name)
        .list();

    return computerResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Computer> listComputers() {

    LOGGER.info("List computers query");

    ArrayList<Computer> computerResult =
        (ArrayList<Computer>) sessionFactory.getCurrentSession().createQuery(LIST_ALL_QUERY).list();

    return computerResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters) {

    LOGGER.info("Select with parameters : " + queryParameters);

    if (queryParameters == null) {
      throw new DaoException("The queryParameters object shouldn't be null");
    }

    String myQuery = new QueryBuilder()
        .append(GET_PAGE_QUERY)
        .orderBy(queryParameters.getByContent(), queryParameters.getOrder())
        .getQuery();

    ArrayList<Computer> computerResult = (ArrayList<Computer>) sessionFactory
        .getCurrentSession()
        .createQuery(myQuery)
        .setString("searchName", "%" + queryParameters.getSearch() + "%")
        .setFirstResult(queryParameters.getOffset())
        .setMaxResults(queryParameters.getPageSize())
        .list();

    return computerResult;
  }

  @Override
  public long getCount(QueryParameters queryParameters) {

    LOGGER.info("Count query with parameters : " + queryParameters);

    long rowCount = ((Long) sessionFactory
        .getCurrentSession()
        .createQuery(GET_COUNT_QUERY)
        .setString("searchName", "%" + queryParameters.getSearch() + "%")
        .uniqueResult()).longValue();

    return rowCount;

  }

  @Override
  public void deleteComputersForCompanyId(long companyId) {

    if (companyId <= 0) {
      throw new DaoException("The company id can't be negative or 0");
    }

    LOGGER.info("Delete computers with company id: " + companyId);

    sessionFactory
        .getCurrentSession()
        .createQuery(DELETE_WHERE_COMPANY_ID)
        .setLong("companyId", companyId)
        .executeUpdate();
  }
}
