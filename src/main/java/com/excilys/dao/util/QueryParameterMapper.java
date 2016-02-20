package com.excilys.dao.util;

import com.excilys.model.QueryParameters;

public interface QueryParameterMapper {

	public static String getPageQuery(QueryParameters queryParameters) {

		QueryBuilder queryBuilder = new QueryBuilder();
		String search = queryParameters.getSearch();

		queryBuilder.select("*").from("computer").leftJoin("company", "computer.COMPANY_ID = company.ID");
		queryBuilder.where("").append("computer.NAME LIKE '%").append(search).append("%' OR company.NAME LIKE '%")
				.append(search).append("%' ");
		queryBuilder.orderBy(queryParameters.getByContent(), queryParameters.getOrder());
		queryBuilder.limit(queryParameters.getPageSize()).offset(queryParameters.getOffset());

		return queryBuilder.getQuery();
	}

	public static String getCountQuery(QueryParameters queryParameters){
		
		QueryBuilder queryBuilder = new QueryBuilder();
		
		queryBuilder.select("COUNT(*)").from("computer");
		
		String search = queryParameters.getSearch();
		
		if(!"%".equals(search)){
			queryBuilder.leftJoin("company", "computer.COMPANY_ID = company.ID");
			queryBuilder.where("").append("computer.NAME LIKE '%").append(queryParameters.getSearch())
					.append("%' OR company.NAME LIKE '%").append(queryParameters.getSearch()).append("%' ");
		}
		
		return queryBuilder.getQuery();
	}

}
