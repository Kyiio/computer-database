package com.excilys.servlets.util;

import javax.servlet.http.HttpServletRequest;

import com.excilys.model.QueryParameters;

public interface QueryParametersBuilder {

	
	public static QueryParameters createQueryParameters(int pageSize, int pageNumber, String search, String order, String by){
		
		QueryParameters queryParameters = new QueryParameters(pageNumber, pageSize);
		queryParameters.setSearch(search);
		queryParameters.setOrder(order);
		queryParameters.setBy(by);
		
		return queryParameters;		
	}

	public static QueryParameters createQueryParameters(HttpServletRequest request){
	
		String pageNumberParameter = request.getParameter("page-number");
		String computerPerPageParameter = request.getParameter("computer-per-page");
		String search = request.getParameter("search-name");
		String order = request.getParameter("order-type");
		String by = request.getParameter("order-by");
		
		int parsedInt;
		int pageSize = 10;
		int pageNumber = 1;

		if (computerPerPageParameter != null && computerPerPageParameter.length() > 0) {
			parsedInt = Integer.parseInt(computerPerPageParameter);
			pageSize = parsedInt;
		}

		if (pageNumberParameter != null && pageNumberParameter.length() > 0) {
			parsedInt = Integer.parseInt(pageNumberParameter);
			pageNumber = parsedInt;
		}
		
		return createQueryParameters(pageSize, pageNumber, search, order, by);
	}
	
}
