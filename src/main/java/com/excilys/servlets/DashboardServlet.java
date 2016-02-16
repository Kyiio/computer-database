package com.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.DTOMapper;
import com.excilys.model.Page;

/**
 * Servlet implementation class CDBServelt
 */
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DashboardServlet() {

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageNumberParameter = request.getParameter("pageNumber");
		String offsetParameter = request.getParameter("computerPerPage");
	
		Page page = Page.getInstance();
				
		int parsedInt;
	
		if(offsetParameter != null){
			parsedInt = Integer.parseInt(offsetParameter);
			Page.setComputerPerPage(parsedInt);
		}
		
		if(pageNumberParameter != null){
			parsedInt = Integer.parseInt(pageNumberParameter);
			page.setPageNumber(parsedInt);
		}
		
		ArrayList<ComputerDTO> computerDTOList = page.getContent();
		
		request.setAttribute("computerList", computerDTOList);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

}
