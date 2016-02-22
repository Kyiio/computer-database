package com.excilys.dao.util;

import com.excilys.dao.exception.DaoException;
import com.excilys.model.QueryParameters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The Interface QueryParameterMapper.
 */
public interface QueryParameterMapper {

  /**
   * Create a preparedStatement from the {@link QueryParameters} object.
   *
   * @param queryParameters
   *          The query parameters
   * @param connection
   *          The connection we need to create the {@link PreparedStatement}
   * @return The prepareStatement build from the {@link QueryParameters}
   */
  public static PreparedStatement createPageQueryAndGetPreparedStatement(
      QueryParameters queryParameters, Connection connection) {

    QueryBuilder queryBuilder = new QueryBuilder();
    String search = "%" + queryParameters.getSearch() + "%";

    queryBuilder.select("*").from("computer").leftJoin("company",
        "computer.COMPANY_ID = company.ID");
    queryBuilder.where("").append("computer.NAME LIKE ? OR company.NAME LIKE ? ");

    // We don't have to replace the by and the order because this datas are
    // checked in the QueryParameters class
    queryBuilder.orderBy(queryParameters.getByContent(), queryParameters.getOrder());
    queryBuilder.limit("?").offset("?");

    String query = queryBuilder.getQuery();
    PreparedStatement preparedStatement;
    try {
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, search);
      preparedStatement.setString(2, search);
      preparedStatement.setInt(3, queryParameters.getPageSize());
      preparedStatement.setInt(4, queryParameters.getOffset());

    } catch (SQLException e) {
      throw new DaoException(
          "Error while trying to create page prepared statement ! Query : " + query, e);
    }

    return preparedStatement;
  }

  /**
   * Create a preparedStatement from the {@link QueryParameters} object.
   *
   * @param queryParameters
   *          The query parameters
   * @param connection
   *          The connection we need to create the {@link PreparedStatement}
   * @return The prepareStatement build from the {@link QueryParameters} that contains the getCount
   *         query
   */
  public static PreparedStatement createCountQueryAndGetPreparedStatement(
      QueryParameters queryParameters, Connection connection) {

    QueryBuilder queryBuilder = new QueryBuilder();

    queryBuilder.select("COUNT(*)").from("computer");

    String search = "%" + queryParameters.getSearch() + "%";

    if (!"%%%".equals(search)) {
      queryBuilder.leftJoin("company", "computer.COMPANY_ID = company.ID");
      queryBuilder.where("").append("computer.NAME LIKE ? OR company.NAME LIKE ?");
    }

    String query = queryBuilder.getQuery();
    PreparedStatement preparedStatement;

    try {
      preparedStatement = connection.prepareStatement(query);

      if (!"%%%".equals(search)) {
        preparedStatement.setString(1, search);
        preparedStatement.setString(2, search);
      }

    } catch (SQLException e) {
      throw new DaoException(
          "Error while trying to create getCount prepared statement ! Query : " + query, e);
    }

    return preparedStatement;
  }

}
