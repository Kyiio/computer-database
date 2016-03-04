package com.excilys.servlets.util;

import com.excilys.dto.ComputerDto;
import com.excilys.dto.PageDto;
import com.excilys.model.QueryParameters;

import java.util.ArrayList;

/**
 * Interface that provides a method to create a page dto from a {@link QueryParameters} object, a
 * list of {@link ComputerDto}, and the total count of computer in the database
 * 
 * @author B. Herbaut
 */
public interface PageCreator {

  /**
   * Builds the page.
   *
   * @param queryParameters
   *          the query parameters
   * @param computerDtoList
   *          the computer dto list
   * @param totalNumberOfComputer
   *          the total number of computer
   * @return the page dto
   */
  public static PageDto buildPage(QueryParameters queryParameters,
      ArrayList<ComputerDto> computerDtoList, long totalNumberOfComputer) {

    int pageSize = queryParameters.getPageSize();
    int maxPageNumber = (int) Math.ceil((double) totalNumberOfComputer / (double) pageSize);

    PageDto page = new PageDto(pageSize, queryParameters.getPageNumber(), totalNumberOfComputer,
        maxPageNumber, computerDtoList, queryParameters.getSearch(), queryParameters.getBy(),
        queryParameters.getOrder());
    return page;
  }

}
