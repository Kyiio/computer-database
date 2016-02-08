package com.excilys.model;

import java.sql.Timestamp;

public class Computer {

	private int id;
	private Company company;
	
	private String name;
	
	private Timestamp discontinued;
	private Timestamp introduced;
	
	public Computer() {
		
	}
	
	public Computer(int id, Company company, String name, Timestamp discontinued, Timestamp introduced) {
		this.id = id;
		this.company = company;
		this.name = name;
		this.discontinued = discontinued;
		this.introduced = introduced;
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
	
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	
	public Timestamp getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(Timestamp introduced) {
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
	
	
}
