package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Page;
import com.excilys.service.impl.ComputerDTOServiceImpl;

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
		
		String pageNumberParameter = request.getParameter("page-number");
		String offsetParameter = request.getParameter("computer-per-page");
	
		Page page = new Page();
				
		int parsedInt;
	
		if(offsetParameter != null){
			parsedInt = Integer.parseInt(offsetParameter);
			page.setComputerPerPage(parsedInt);
		}
		
		if(pageNumberParameter != null){
			parsedInt = Integer.parseInt(pageNumberParameter);
			page.setPageNumber(parsedInt);
		}

		ComputerDTOServiceImpl.getInstance().setPageContent(page);
		
		request.setAttribute("totalComputerFound", ComputerDTOServiceImpl.getInstance().getCount());
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
