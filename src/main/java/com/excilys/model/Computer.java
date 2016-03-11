package com.excilys.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class represents a computer from the database. A computer is identified by its id and must
 * possess a name. It can also possess other information like the company that owns it and the
 * introduced and discontinued date
 * 
 * @author B. Herbaut
 */

@Entity
@Table(name = "computer")
public class Computer implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private long      id;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company   company;

  @Column(name = "name")
  private String    name;

  @Column(name = "discontinued")
  private LocalDate discontinued;

  @Column(name = "introduced")
  private LocalDate introduced;

  public Computer() {

  }

  /**
   * Instantiates a new computer.
   *
   * @param id the id
   * @param company the company
   * @param name the name
   * @param discontinued the discontinued
   * @param introduced the introduced
   */
  public Computer(long id, Company company, String name, LocalDate discontinued,
      LocalDate introduced) {
    this.id = id;
    this.company = company;
    this.name = name;
    this.discontinued = discontinued;
    this.introduced = introduced;
  }

  /**
   * Instantiates a new computer.
   *
   * @param computer the computer
   */
  public Computer(Computer computer) {
    this.id = computer.id;
    this.company = new Company(computer.company);
    this.name = computer.name;
    this.discontinued = computer.discontinued;
    this.introduced = computer.introduced;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(LocalDate discontinued) {
    this.discontinued = discontinued;
  }

  public LocalDate getIntroduced() {
    return introduced;
  }

  public void setIntroduced(LocalDate introduced) {
    this.introduced = introduced;
  }

  @Override
  public String toString() {

    StringBuilder strBuilder = new StringBuilder("Computer [id=");
    strBuilder.append(id).append(", company=").append(company).append(", name=").append(name);
    strBuilder.append(", discontinued=").append(discontinued).append(", introduced=")
        .append(introduced);
    strBuilder.append("]\n");

    return strBuilder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
    Computer other = (Computer) obj;
    if (company == null) {
      if (other.company != null) {
        return false;
      }
    } else if (!company.equals(other.company)) {
      return false;
    }
    if (discontinued == null) {
      if (other.discontinued != null) {
        return false;
      }
    } else if (!discontinued.equals(other.discontinued)) {
      return false;
    }
    if (id != other.id) {
      return false;
    }
    if (introduced == null) {
      if (other.introduced != null) {
        return false;
      }
    } else if (!introduced.equals(other.introduced)) {
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
