package com.excilys.model;

/**
 * This class represents a company from the database, a company is identified by its id and possess a name
 * @author Bastien Herbaut
 */
public class Company {

	private int id;
	private String name;
	
	public Company() {
		
	}
	
	public Company(Company company){
		this.id = company.id;
		this.name = company.name;
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
		strBuilder.append(id).append(", name=").append(name).append("]");
				
		return strBuilder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
