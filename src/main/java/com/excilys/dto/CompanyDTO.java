package com.excilys.dto;

public class CompanyDTO {

	private int id;
	private String name;

	public CompanyDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
