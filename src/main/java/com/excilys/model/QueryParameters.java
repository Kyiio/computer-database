package com.excilys.model;

/**
 * Class that represents all the parameters that we need to make a query
 * 
 * @author B. Herbaut
 */
public class QueryParameters {

  enum OrderBy {
    COMPUTER_ID("computer.id"),
    COMPUTER_NAME("computer.name"),
    INTRODUCED("computer.introduced"),
    DISCONTINUED("computer.discontinued"),
    COMPANY_NAME("company.name");

    String content;

    OrderBy(String content) {
      this.content = content;
    }
  }

  enum Order {
    ASC,
    DESC
  }

  int offset;
  int pageSize;
  int pageNumber;

  String search;
  Order order;
  OrderBy by;

  public QueryParameters() {
    this(1, 10);
  }

  /**
   * Create a queryParameter object with the given parameters. If you don't want to set some
   * parameters just put -1 or null.
   *
   * @param pageNumber
   *          the page number
   * @param pageSize
   *          the page size
   */
  public QueryParameters(int pageNumber, int pageSize) {
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
    this.offset = (pageNumber - 1) * pageSize;

    this.search = "%";
    this.order = Order.ASC;
    this.by = OrderBy.COMPUTER_ID;
  }

  public int getOffset() {
    return offset;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
    this.offset = (pageNumber - 1) * pageSize;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
    this.offset = (pageNumber - 1) * pageSize;
  }

  public String getSearch() {
    return search;
  }

  /**
   * Sets the search.
   *
   * @param search
   *          the new search
   */
  public void setSearch(String search) {
    if (search != null && search.length() > 0) {
      this.search = search;
    }
  }

  public String getOrder() {
    return order.toString();
  }

  /**
   * Sets the order.
   *
   * @param order
   *          the new order
   */
  public void setOrder(String order) {
    if (order != null && order.length() > 0) {
      this.order = Order.valueOf(order);
    }
  }

  public String getBy() {
    return by.toString();
  }

  public String getByContent() {
    return by.content;
  }

  /**
   * Sets the by.
   *
   * @param by
   *          the new by
   */
  public void setBy(String by) {
    if (by != null && by.length() > 0) {
      this.by = OrderBy.valueOf(by);
    }
  }

  @Override
  public String toString() {
    return "QueryParameters [offset=" + offset + ", pageSize=" + pageSize + ", pageNumber="
        + pageNumber + ", search=" + search + ", order=" + order + ", by=" + by + "]";
  }

}
