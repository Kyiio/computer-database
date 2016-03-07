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

  private ArrayList<ComputerDto> content;

  private int                    pageSize;

  private int                    maxPageNumber;

  private int                    pageNumber;

  private long                   totalNumberOfComputer;

  private String                 searchName;

  private String                 orderBy;

  private String                 orderType;

  public PageDto() {
    super();
  }

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
    this.orderBy = orderBy;
    this.orderType = orderType;
  }

  public ArrayList<ComputerDto> getContent() {
    return content;
  }

  public void setContent(ArrayList<ComputerDto> content) {
    this.content = content;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getMaxPageNumber() {
    return maxPageNumber;
  }

  public void setMaxPageNumber(int maxPageNumber) {
    this.maxPageNumber = maxPageNumber;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public long getTotalNumberOfComputer() {
    return totalNumberOfComputer;
  }

  public void setTotalNumberOfComputer(long totalNumberOfComputer) {
    this.totalNumberOfComputer = totalNumberOfComputer;
  }

  public String getSearchName() {
    return searchName;
  }

  public void setSearchName(String searchName) {
    this.searchName = searchName;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orederBy) {
    this.orderBy = orederBy;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  @Override
  public String toString() {
    return "PageDto [content=" + content + ", pageSize=" + pageSize + ", maxPageNumber="
        + maxPageNumber + ", pageNumber=" + pageNumber + ", totalNumberOfComputer="
        + totalNumberOfComputer + ", searchName=" + searchName + ", orederBy=" + orderBy
        + ", orderType=" + orderType + "]";
  }

}
