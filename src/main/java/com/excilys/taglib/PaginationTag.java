package com.excilys.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PaginationTag extends SimpleTagSupport {

	private int pageNumber;
	private int maxPageNumber;
	private int computerPerPage;
	
	public int getPageNumber() {
		return pageNumber;
	}

	public int getMaxPageNumber() {
		return maxPageNumber;
	}

	public int getComputerPerPage() {
		return computerPerPage;
	}

	public void setMaxPageNumber(int maxPageNumber) {
		this.maxPageNumber = maxPageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setComputerPerPage(int computerPerPage) {
		this.computerPerPage = computerPerPage;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		
		//Previous button
		out.print("<li><a href=\"");
		if(pageNumber <= 1){
			out.print("#");
		}
		else{
			out.print(getLink(pageNumber-1));
		}
		out.println("\" aria-label=\"Previous\"> <spanaria-hidden=\"true\">&laquo;</span></a></li>");
		
		//Page buttons
		String link;
		int limit = (pageNumber + 10 < maxPageNumber ? pageNumber + 10 : maxPageNumber);
		
		for(int i = (pageNumber - 10 > 1 ? pageNumber - 10 : 1); i <= limit; ++i){
			
			link = "<li ";
			
			if(i == pageNumber){
				link += "class=\"active\"";
			}
			
			link += "><a href=\"" + getLink(i) + "\">"+i+"</span></a></li>";
			out.println(link);
		}

		//Next button
		out.print("<li><a href=\"");
		if(pageNumber >= maxPageNumber){
			out.print("#");
		}
		else{
			out.print(getLink(pageNumber+1));
		}
		out.println("\" aria-label=\"Next\"> <spanaria-hidden=\"true\">&raquo;</span></a></li>");
	}
	
	private String getLink(int numPage){
		
		String result = "";
		
		if(numPage == this.pageNumber){
			result = "#";
		}else{
			result = "dashboard?pageNumber=" + numPage + "&computerPerPage=" + computerPerPage ;
		}
		
		return result;
	}
}
