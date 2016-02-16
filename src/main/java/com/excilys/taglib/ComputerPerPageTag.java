package com.excilys.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ComputerPerPageTag extends SimpleTagSupport {

	private int computerPerPage;

	public int getComputerPerPage() {
		return computerPerPage;
	}

	public void setComputerPerPage(int computerPerPage) {
		this.computerPerPage = computerPerPage;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		
		int[] choices = {10,50,100};

		int currentPageNumber;
		
		for(int i = 0; i < choices.length; ++i){
			
			currentPageNumber = choices[i];
			out.print("<a ");

			if(computerPerPage == currentPageNumber){
				out.print("href=\"#\" class=\"btn disabled\"");
			}
			else{
				out.print("href=\"dashboard?computerPerPage="+ currentPageNumber + "\" class=\"btn btn-default\"");
			}
			
			out.print(">"+currentPageNumber+"</a>");
			
		}
	}
}