package com.excilys.dto;

import com.excilys.model.Company;

public class CompanyDTO {

	private int id;
	private String name;

	public CompanyDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public CompanyDTO(Company company) {
		this(company.getId(), company.getName());
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
