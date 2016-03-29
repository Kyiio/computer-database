package com.excilys.dto.util;

import com.excilys.dto.PageDto;
import com.excilys.model.QueryParameters;

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
   * @param pageDto the page dto
   * @return the query parameters
   */
  public static QueryParameters createQueryParameters(PageDto pageDto) {

    return createQueryParameters(pageDto.getPageSize(), pageDto.getPageNumber(),
        pageDto.getSearchName(), pageDto.getOrderType(), pageDto.getOrderBy());
  }

}
