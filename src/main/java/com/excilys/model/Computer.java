package com.excilys.model;

import java.time.LocalDateTime;

/**
 * This class represents a computer from the database. A computer is identified by its id and must possess a name.
 * It can also possess other information like the company that owns it and the introduced and discontinued date
 * @author Bastien Herbaut
 */
public class Computer {

	private int id;
	private Company company;
	
	private String name;
	
	private LocalDateTime discontinued;
	private LocalDateTime introduced;
	
	public Computer() {
		
	}
	
	public Computer(int id, Company company, String name, LocalDateTime discontinued, LocalDateTime introduced) {
		this.id = id;
		this.company = company;
		this.name = name;
		this.discontinued = discontinued;
		this.introduced = introduced;
	}
	
	public Computer(Computer computer){
		this.id = computer.id;
		this.company = new Company(computer.company);
		this.name = computer.name;
		this.discontinued = computer.discontinued;
		this.introduced = computer.introduced;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
	
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}
	
	public LocalDateTime getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}

	@Override
	public String toString() {
		
		StringBuilder strBuilder = new StringBuilder("Computer [id=");
		strBuilder.append(id).append(", company=").append(company).append(", name=").append(name);
		strBuilder.append(", discontinued=").append(discontinued).append(", introduced=").append(introduced);
		strBuilder.append("]\n");
		
		return strBuilder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
