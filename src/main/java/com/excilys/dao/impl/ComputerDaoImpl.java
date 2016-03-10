package com.excilys.dao.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.exception.DaoException;
import com.excilys.dao.mapper.ComputerDaoMapper;
import com.excilys.dao.util.QueryBuilder;
import com.excilys.dao.util.QueryParameterMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository("computerDao")
public class ComputerDaoImpl implements ComputerDao {

  private static final String INSERT_QUERY      =
      "INSERT INTO computer(COMPANY_ID, DISCONTINUED, INTRODUCED, NAME) VALUES (?, ?, ?, ?);";
  private static final String UPDATE_QUERY      =
      "UPDATE computer SET COMPANY_ID=?, DISCONTINUED=?, INTRODUCED=?, NAME=? WHERE ID=?";
  private static final String DELETE_QUERY      = "DELETE FROM computer WHERE ID=?";

  private static final String LIST_ALL_QUERY    =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID";
  private static final String GET_BY_ID_QUERY   =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID WHERE computer.ID = ?";
  private static final String GET_BY_NAME_QUERY =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID WHERE computer.NAME = ?";

  @Autowired
  private JdbcTemplate        jdbcTemplate;
  @Autowired
  private ComputerDaoMapper   computerDaoMapper;

  @Override
  public long insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) {

    if (name == null || name.length() <= 0) {
      throw new DaoException("The name of the computer must be set !");
    }

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) {
        PreparedStatement preparedStatement;
        try {
          preparedStatement =
              connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

          if (company == null) {
            preparedStatement.setNull(1, java.sql.Types.BIGINT);
          } else {
            preparedStatement.setLong(1, company.getId());
          }

          if (discontinued == null) {
            preparedStatement.setNull(2, java.sql.Types.TIMESTAMP);
          } else {
            preparedStatement.setTimestamp(2, Timestamp.valueOf(discontinued.atStartOfDay()));
          }

          if (introduced == null) {
            preparedStatement.setNull(3, java.sql.Types.TIMESTAMP);
          } else {
            preparedStatement.setTimestamp(3, Timestamp.valueOf(introduced.atStartOfDay()));
          }

          preparedStatement.setString(4, name);

        } catch (SQLException e) {

          throw new DaoException("Error while trying to create the insert computer statement", e);
        }

        return preparedStatement;
      }
    }, keyHolder);

    return keyHolder.getKey().longValue();
  }

  @Override
  public void updateComputer(Computer computer) {

    if (computer == null) {
      throw new DaoException("The given computer is null !");
    }

    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) {

        PreparedStatement preparedStatement;
        try {
          preparedStatement = connection.prepareStatement(UPDATE_QUERY);

          if (computer.getCompany() == null) {
            preparedStatement.setNull(1, java.sql.Types.BIGINT);
          } else {
            preparedStatement.setLong(1, computer.getCompany().getId());
          }

          if (computer.getDiscontinued() == null) {
            preparedStatement.setNull(2, java.sql.Types.TIMESTAMP);
          } else {
            preparedStatement.setTimestamp(2,
                Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
          }

          if (computer.getIntroduced() == null) {
            preparedStatement.setNull(3, java.sql.Types.TIMESTAMP);
          } else {
            preparedStatement.setTimestamp(3,
                Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
          }

          preparedStatement.setString(4, computer.getName());
          preparedStatement.setLong(5, computer.getId());

        } catch (SQLException e) {

          throw new DaoException("Error while trying to create the update computer statement", e);
        }

        return preparedStatement;
      }
    });
  }

  @Override
  public void deleteComputer(long id) throws DaoException {

    jdbcTemplate.update(DELETE_QUERY, id);
  }

  @Override
  public Computer getById(long id) throws DaoException {

    Computer computerResult = null;

    ArrayList<Computer> computerList =
        (ArrayList<Computer>) jdbcTemplate.query(GET_BY_ID_QUERY, computerDaoMapper, id);

    if (computerList.size() > 0) {
      computerResult = computerList.get(0);
    }

    return computerResult;
  }

  @Override
  public ArrayList<Computer> getByName(String name) {

    ArrayList<Computer> computerResult =
        (ArrayList<Computer>) jdbcTemplate.query(GET_BY_NAME_QUERY, computerDaoMapper, name);

    return computerResult;
  }

  @Override
  public ArrayList<Computer> listComputers() throws DaoException {

    ArrayList<Computer> computerResult =
        (ArrayList<Computer>) jdbcTemplate.query(LIST_ALL_QUERY, computerDaoMapper);

    return computerResult;
  }

  @Override
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters) {

    if (queryParameters == null) {
      throw new DaoException("The queryParameters object shouldn't be null");
    }

    String query = QueryParameterMapper.createPageQuery(queryParameters);

    String search = "%" + queryParameters.getSearch() + "%";
    ArrayList<Computer> computerResult =
        (ArrayList<Computer>) jdbcTemplate.query(query, computerDaoMapper, search, search,
            queryParameters.getPageSize(), queryParameters.getOffset());

    return computerResult;
  }

  @Override
  public long getCount(QueryParameters queryParameters) {

    String search = "%" + queryParameters.getSearch() + "%";

    String query = QueryParameterMapper.createCountQuery();

    long rowCount = jdbcTemplate.queryForObject(query, new Object[] { search, search }, Long.class);

    return rowCount;
  }

  @Override
  public void deleteComputersForCompanyId(long companyId) {

    QueryBuilder queryBuilder = new QueryBuilder();
    queryBuilder.deleteFrom("computer").where("COMPANY_ID = ?");
    String query = queryBuilder.getQuery();

    jdbcTemplate.update(query, companyId);

  }
}
