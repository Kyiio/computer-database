package com.excilys.dto;

import java.util.ArrayList;

/**
 * Class that represents a page in the web app. It contains the list of computer DTO in the page,
 * the page number, the number of computer per page, the maximum number of page and the number of
 * computer in the database for the number of computer per page.
 * 
 * @author B. Herbaut
 */
public class PageDto {

  /** The content. */
  private ArrayList<ComputerDto> content;

  /** The page size. */
  private int                    pageSize;

  /** The max page number. */
  private int                    maxPageNumber;

  /** The page number. */
  private int                    pageNumber;

  /** The total number of computer. */
  private long                   totalNumberOfComputer;

  /** The search name. */
  private String                 searchName;

  /** The oreder by. */
  private String                 orederBy;

  /** The order type. */
  private String                 orderType;

  /**
   * Instantiates a new page dto.
   *
   * @param pageSize the page size
   * @param pageNumber the page number
   * @param totalNumberOfComputer the total number of computer
   * @param maxPageNumber the max page number
   * @param content the content
   * @param searchName the search name
   * @param orderBy the order by
   * @param orderType the order type
   */
  public PageDto(int pageSize, int pageNumber, long totalNumberOfComputer, int maxPageNumber,
      ArrayList<ComputerDto> content, String searchName, String orderBy, String orderType) {
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
    this.totalNumberOfComputer = totalNumberOfComputer;
    this.maxPageNumber = maxPageNumber;
    this.content = content;
    this.searchName = searchName;
    this.orederBy = orderBy;
    this.orderType = orderType;
  }

  /**
   * Gets the content.
   *
   * @return the content
   */
  public ArrayList<ComputerDto> getContent() {
    return content;
  }

  /**
   * Gets the page size.
   *
   * @return the page size
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Gets the max page number.
   *
   * @return the max page number
   */
  public int getMaxPageNumber() {
    return maxPageNumber;
  }

  /**
   * Gets the page number.
   *
   * @return the page number
   */
  public int getPageNumber() {
    return pageNumber;
  }

  /**
   * Gets the total number of computer.
   *
   * @return the total number of computer
   */
  public long getTotalNumberOfComputer() {
    return totalNumberOfComputer;
  }

  /**
   * Gets the search name.
   *
   * @return the search name
   */
  public String getSearchName() {
    return searchName;
  }

  /**
   * Gets the order by.
   *
   * @return the order by
   */
  public String getOrderBy() {
    return orederBy;
  }

  /**
   * Gets the order type.
   *
   * @return the order type
   */
  public String getOrderType() {
    return orderType;
  }
}
