package com.excilys.dto;

/**
 * DTO representation of the model class Company
 * 
 * @author B. Herbaut
 * @see com.excilys.model.Company
 */
public class CompanyDto {

  private long   id;
  private String name;

  public CompanyDto(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
