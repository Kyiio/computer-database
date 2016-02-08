package com.excilys.model;

public class Company {

	private int id;
	private String name;
	
	public Company() {
		
	}
	
	public Company(int id) {
		this.id = id;
	}

	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
		strBuilder.append(id).append(", name=").append(name);
				
		return strBuilder.toString();
	}	
}
