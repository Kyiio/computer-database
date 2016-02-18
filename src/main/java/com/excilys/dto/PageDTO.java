package com.excilys.dto;

import java.util.ArrayList;

/**
 * Class that represents a page in the web app. It contains the list of computer
 * DTO in the page, the page number, the number of computer per page, the
 * maximum number of page and the number of computer in the database for the
 * number of computer per page.
 * 
 * @author B. Herbaut
 */
public class PageDTO {

	private ArrayList<ComputerDTO> content;
	private int pageSize;
	private int maxPageNumber;
	private int pageNumber;
	private int totalNumberOfComputer;

	public PageDTO(int pageSize, int pageNumber, int totalNumberOfComputer, int maxPageNumber,
			ArrayList<ComputerDTO> content) {
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.totalNumberOfComputer = totalNumberOfComputer;
		this.maxPageNumber = maxPageNumber;
		this.content = content;
	}

	public ArrayList<ComputerDTO> getContent() {
		return content;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getMaxPageNumber() {
		return maxPageNumber;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getTotalNumberOfComputer() {
		return totalNumberOfComputer;
	}
}
