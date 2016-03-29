package com.excilys.dto.util;

import com.excilys.dto.ComputerDto;
import com.excilys.dto.PageDto;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerDtoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Interface that provides a method to create a page dto from a {@link QueryParameters} object, a
 * list of {@link ComputerDto}, and the total count of computer in the database
 * 
 * @author B. Herbaut
 */
@Component
public final class PageCreator {

  @Autowired
  private ComputerDtoService computerDtoService;

  /**
   * Builds the page.
   *
   * @param queryParameters the query parameters
   * @param totalNumberOfComputer the total number of computer
   * @return the page dto
   */
  public PageDto buildPage(QueryParameters queryParameters, long totalNumberOfComputer) {

    int pageSize = queryParameters.getPageSize();
    int maxPageNumber = (int) Math.ceil((double) totalNumberOfComputer / (double) pageSize);

    int pageNumber = queryParameters.getPageNumber();

    if (pageNumber > maxPageNumber) {
      queryParameters.setPageNumber(maxPageNumber);
    }

    // We retrieve the needed computers from the database
    ArrayList<ComputerDto> computerDtoList =
        computerDtoService.selectWithParameters(queryParameters);

    PageDto page = new PageDto(pageSize, queryParameters.getPageNumber(), totalNumberOfComputer,
        maxPageNumber, computerDtoList, queryParameters.getSearch(), queryParameters.getBy(),
        queryParameters.getOrder());
    return page;
  }

  /**
   * Update page dto.
   *
   * @param pageDto the page dto
   * @return the page dto from previous one
   */
  public PageDto getPageDtoFromPreviousOne(PageDto pageDto) {

    if (pageDto.getPageSize() < 1) {
      pageDto.setPageSize(10);
    }

    if (pageDto.getPageNumber() < 1) {
      pageDto.setPageNumber(1);
    }

    // We build the query parameters from the data in the pageDto object
    QueryParameters queryParameters = QueryParametersBuilder.createQueryParameters(pageDto);
    // We retrieve the number of computer in the database
    long computerCount = computerDtoService.getCount(queryParameters);

    pageDto = buildPage(queryParameters, computerCount);

    return pageDto;
  }

}
