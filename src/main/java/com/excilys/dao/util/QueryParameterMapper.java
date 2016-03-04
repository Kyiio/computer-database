package com.excilys.dao.util;

import com.excilys.model.QueryParameters;

/**
 * The Interface QueryParameterMapper.
 */
public interface QueryParameterMapper {

  /**
   * Creates the page query.
   *
   * @param queryParameters the query parameters
   * @return the prepared statement
   */
  public static String createPageQuery(QueryParameters queryParameters) {

    QueryBuilder queryBuilder = new QueryBuilder();

    queryBuilder.select("*").from("computer").leftJoin("company",
        "computer.COMPANY_ID = company.ID");
    queryBuilder.where("").append("computer.NAME LIKE ? OR company.NAME LIKE ? ");

    // We don't have to replace the by and the order because this datas are
    // checked in the QueryParameters class
    queryBuilder.orderBy(queryParameters.getByContent(), queryParameters.getOrder());
    queryBuilder.limit("?").offset("?");

    return queryBuilder.getQuery();
  }

  /**
   * Creates the count query.
   *
   * @param queryParameters the query parameters
   * @return the prepared statement
   */
  public static String createCountQuery(QueryParameters queryParameters) {

    QueryBuilder queryBuilder = new QueryBuilder();

    queryBuilder.select("COUNT(*)").from("computer");

    queryBuilder.leftJoin("company", "computer.COMPANY_ID = company.ID");
    queryBuilder.where("").append("computer.NAME LIKE ? OR company.NAME LIKE ?");

    return queryBuilder.getQuery();
  }

}
