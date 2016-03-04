package com.excilys.dao.util;

public class QueryBuilder {

  StringBuilder queryContent;

  public QueryBuilder() {
    queryContent = new StringBuilder();
  }

  public QueryBuilder select(String select) {
    queryContent.append("SELECT ").append(select).append(" ");
    return this;
  }

  public QueryBuilder deleteFrom(String table) {
    queryContent.append("DELETE FROM ").append(table).append(" ");
    return this;
  }

  public QueryBuilder leftJoin(String table, String on) {
    queryContent.append("LEFT JOIN ").append(table).append(" ON ").append(on).append(" ");
    return this;
  }

  public QueryBuilder from(String from) {
    queryContent.append("FROM ").append(from).append(" ");
    return this;
  }

  public QueryBuilder where(String where) {
    queryContent.append("WHERE ").append(where).append(" ");
    return this;
  }

  public QueryBuilder orderBy(String by, String order) {
    queryContent.append("ORDER BY ").append(by).append(" ").append(order).append(" ");
    return this;
  }

  public QueryBuilder offset(String offset) {
    queryContent.append("OFFSET ").append(offset).append(" ");
    return this;
  }

  public QueryBuilder limit(String limit) {
    queryContent.append("LIMIT ").append(limit).append(" ");
    return this;
  }

  public QueryBuilder append(String str) {
    queryContent.append(str);
    return this;
  }

  public QueryBuilder append(int number) {
    queryContent.append(number);
    return this;
  }

  public QueryBuilder append(long number) {
    queryContent.append(number);
    return this;
  }

  public String getQuery() {
    return queryContent.toString();
  }
}
