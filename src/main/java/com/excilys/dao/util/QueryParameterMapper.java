package com.excilys.dao.util;

import com.excilys.model.QueryParameters;

public interface QueryParameterMapper {

	public static String getPageQuery(QueryParameters queryParameters) {

		QueryBuilder queryBuilder = new QueryBuilder();

		queryBuilder.select("*").from("computer").leftJoin("company", "computer.COMPANY_ID = company.ID");
		queryBuilder.where("").append("computer.NAME LIKE'").append(queryParameters.getSearch())
				.append("' OR company.NAME LIKE '").append(queryParameters.getSearch()).append("' ");
		queryBuilder.orderBy(queryParameters.getByContent(), queryParameters.getOrder());
		queryBuilder.limit(queryParameters.getPageSize()).offset(queryParameters.getOffset());

		return queryBuilder.getQuery();
	}

}
