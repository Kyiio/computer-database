package com.excilys.dto;

import java.io.Serializable;

/**
 * DTO representation of the model class Company
 * 
 * @author B. Herbaut
 * @see com.excilys.model.Company
 */
public class CompanyDto implements Serializable {

  private static final long serialVersionUID = 697013146781175536L;

  private long              id;
  private String            name;

  public CompanyDto() {
    super();
  }

  public CompanyDto(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return new StringBuilder("Company's id : ")
        .append(id)
        .append(", company's name : ")
        .append(name)
        .toString();
  }

}
