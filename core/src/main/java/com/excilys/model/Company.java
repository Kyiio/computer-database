package com.excilys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents a company from the database, a company is identified by its id and possess
 * a name
 * 
 * @author B. Herbaut
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private long   id;
  @Column(name = "name")
  private String name;

  public Company() {

  }

  public Company(Company company) {
    this.id = company.id;
    this.name = company.name;
  }

  public Company(long id) {
    this.id = id;
  }

  public Company(long id, String name) {
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

    StringBuilder strBuilder = new StringBuilder("Company [id=");
    strBuilder.append(id).append(", name=").append(name).append("]");

    return strBuilder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Company other = (Company) obj;
    if (id != other.id) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

}
