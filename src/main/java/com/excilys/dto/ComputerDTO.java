package com.excilys.dto;

/**
 * DTO representation of the model class Computer
 * @author B. Herbaut
 * @see com.excilys.model.Computer
 */
public class ComputerDTO {

	private int computerId;
	private String computerName;
	private String introducedDate;
	private String discontinuedDate;

	private int companyId;
	private String companyName;

	public ComputerDTO(int computerId, String computerName, String introducedDate, String discontinuedDate, int companyId, String companyName) {
		this.computerId = computerId;
		this.computerName = computerName;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.companyId = companyId;
		this.companyName = companyName;
	}

	public int getComputerId() {
		return computerId;
	}

	public String getComputerName() {
		return computerName;
	}

	public String getIntroducedDate() {
		return introducedDate;
	}

	public String getDiscontinuedDate() {
		return discontinuedDate;
	}

	public int getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}
}
