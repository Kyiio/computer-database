package com.excilys.dto;

public class PageDTO {

	private int pageMaxNumber;
	private int pageNumber;
	private int nbComputerPerPage;
	
	public PageDTO(int pageMaxNumber, int pageNumber, int nbComputerPerPage) {
		this.pageMaxNumber = pageMaxNumber;
		this.pageNumber = pageNumber;
		this.nbComputerPerPage = nbComputerPerPage;
	}
	
	public int getPageMaxNumber() {
		return pageMaxNumber;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public int getNbComputerPerPage() {
		return nbComputerPerPage;
	}
}
