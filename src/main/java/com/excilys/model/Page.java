package com.excilys.model;

import java.util.ArrayList;

import com.excilys.dao.ComputerDAO;
import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.PageException;
import com.excilys.service.impl.ComputerDTOServiceImpl;

public class Page {
	
	private final static Page INSTANCE;
	
	private static int maxPageNumber;
	private static int computerPerPage;
	
	private int pageNumber;
	private ArrayList<ComputerDTO> content;
	
	static{
		INSTANCE = new Page(10,1);
	}
		
	private Page(int nbComputerPerPage, int pageNum) {		
		Page.computerPerPage = nbComputerPerPage;
		pageNumber = pageNum;
		
		updateContent();
		updateMaxPageNumber();
	}

	private ArrayList<ComputerDTO> getContentFromDTOService(){
		return ComputerDTOServiceImpl.getInstance().getXComputersStartingAtIndexY(computerPerPage, pageNumber-1);
	}
	
	public static Page getInstance(){
		return INSTANCE;
	}

	public static int getMaxPageNum() {
		return maxPageNumber;
	}

	public int getMaxPageNumber() {
		return maxPageNumber;
	}
	
	public static int getComputerPerPageNum() {
		return computerPerPage;
	}

	public int getComputerPerPage() {
		return computerPerPage;
	}
	
	public static void setComputerPerPage(int computerPerPage) {
		
		if(computerPerPage <= 0 /*|| computerPerPage != 10 || computerPerPage != 50 || computerPerPage != 100*/){
			throw new PageException("Invalid number of computer per page " + computerPerPage + "!");
		}
		
		if(Page.computerPerPage != computerPerPage){
			
			Page.computerPerPage = computerPerPage;
			
			INSTANCE.updateMaxPageNumber();
			INSTANCE.setPageNumber(1);
			INSTANCE.updateContent();
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) throws PageException{

		if(pageNumber < 1 || pageNumber > maxPageNumber){
			throw new PageException("Page not found !\nThe page with number " + pageNumber + " doesn't exists !");
		}
		else{
			this.pageNumber = pageNumber;
			updateContent();
		}
	}
	
	public ArrayList<ComputerDTO> getContent(){
		return content;
	}
	
	private void updateContent(){
		content = getContentFromDTOService();
	}
	
	private void updateMaxPageNumber(){
		ComputerDAO computerDao = ComputerDAOImpl.getInstance();
		Page.maxPageNumber = (int)Math.ceil((double)computerDao.getNbComputer()/(double)computerPerPage);
	}
}
