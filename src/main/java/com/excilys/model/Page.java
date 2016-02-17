package com.excilys.model;

import java.util.ArrayList;

import com.excilys.dao.ComputerDAO;
import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.PageException;

/**
 * Class that represents a page in the web app. It contains the list of computer
 * DTO in the page, the page number, the number of computer per page and the
 * maximum number of page for the number of computer per page.
 * 
 * @author B. Herbaut
 */
public class Page {

	private ArrayList<ComputerDTO> content;
	private int computerPerPage;
	private int maxPageNumber;
	private int pageNumber;

	public Page() {
		this(10, 1);
	}

	public Page(int nbComputerPerPage, int pageNum) {
		computerPerPage = nbComputerPerPage;
		pageNumber = pageNum;
		updateMaxPageNumber();
	}

	private void updateMaxPageNumber() {
		ComputerDAO computerDao = ComputerDAOImpl.getInstance();
		maxPageNumber = (int) Math.ceil((double) computerDao.getCount() / (double) computerPerPage);
	}

	public int getMaxPageNumber() {
		return maxPageNumber;
	}

	public int getComputerPerPage() {
		return computerPerPage;
	}

	/**
	 * Method that check if the computer per page number is valid before setting
	 * the value. It is valid if it is superior to 0. Else it throws a
	 * PageException.
	 * 
	 * @param computerPerPage
	 */
	public void setComputerPerPage(int computerPerPage) {

		if (computerPerPage <= 0 /*
									 * || computerPerPage != 10 ||
									 * computerPerPage != 50 || computerPerPage
									 * != 100
									 */) {
			throw new PageException("Invalid number of computer per page " + computerPerPage + "!");
		}

		if (this.computerPerPage != computerPerPage) {

			this.computerPerPage = computerPerPage;

			updateMaxPageNumber();
			setPageNumber(1);
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * Method that check if the page number is valid ( superior to 0 and
	 * inferior or equal to the max page number) before setting the value. If it
	 * isn't valid it throws a PageException
	 * 
	 * @param pageNumber
	 * @throws PageException
	 */
	public void setPageNumber(int pageNumber) throws PageException {

		if (pageNumber < 1 || pageNumber > maxPageNumber) {
			throw new PageException("Page not found !\nThe page with number " + pageNumber + " doesn't exists !");
		} else {
			this.pageNumber = pageNumber;
		}
	}

	public ArrayList<ComputerDTO> getContent() {
		return content;
	}

	public void setContent(ArrayList<ComputerDTO> computerDTOList) {
		content = computerDTOList;
	}

}
