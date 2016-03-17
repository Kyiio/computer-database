package com.excilys.controller.util;

import com.excilys.dto.PageDto;
import com.excilys.model.QueryParameters;

import javax.servlet.http.HttpServletRequest;

public interface QueryParametersBuilder {

  /**
   * Creates the query parameters.
   *
   * @param pageSize the page size
   * @param pageNumber the page number
   * @param search the search
   * @param order the order
   * @param by the by
   * @return the query parameters
   */
  public static QueryParameters createQueryParameters(int pageSize, int pageNumber, String search,
      String order, String by) {

    QueryParameters queryParameters = new QueryParameters(pageNumber, pageSize);
    queryParameters.setSearch(search);
    queryParameters.setOrder(order);
    queryParameters.setBy(by);

    return queryParameters;
  }

  /**
   * Creates the query parameters.
   *
   * @param request the request
   * @return the query parameters
   */
  public static QueryParameters createQueryParameters(HttpServletRequest request) {

    String pageNumberParameter = request.getParameter("pageNumber");
    String computerPerPageParameter = request.getParameter("pageSize");
    String search = request.getParameter("searchName");
    String order = request.getParameter("orderType");
    String by = request.getParameter("orderBy");

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

  /**
   * Creates the query parameters.
   *
   * @param pageDto the page dto
   * @return the query parameters
   */
  public static QueryParameters createQueryParameters(PageDto pageDto) {

    int pageSize = pageDto.getPageSize();
    int pageNumber = pageDto.getPageNumber();

    if (pageSize < 1) {
      pageSize = 10;
    }

    if (pageNumber < 1) {
      pageNumber = 1;
    }

    return createQueryParameters(pageSize, pageNumber, pageDto.getSearchName(),
        pageDto.getOrderType(), pageDto.getOrderBy());
  }

}
