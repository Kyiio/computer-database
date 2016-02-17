package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Page;
import com.excilys.service.impl.ComputerDTOServiceImpl;

/**
 * Servlet implementation class DashboardServlet.
 * 
 * This class shows the list of all the computer in pages. It also manage the
 * deletion of computer.
 * 
 * @author B. Herbaut
 */
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DashboardServlet() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// We first retrieve the page parameters

		String pageNumberParameter = request.getParameter("page-number");
		String offsetParameter = request.getParameter("computer-per-page");

		// If they were in the URL we creata a page object if this informations
		Page page = new Page();

		int parsedInt;

		if (offsetParameter != null) {
			parsedInt = Integer.parseInt(offsetParameter);
			page.setComputerPerPage(parsedInt);
		}

		if (pageNumberParameter != null) {
			parsedInt = Integer.parseInt(pageNumberParameter);
			page.setPageNumber(parsedInt);
		}

		// And we retrieve the needed computers from the database
		ComputerDTOServiceImpl.getInstance().setPageContent(page);

		// Finally we send all this information to the JSP page
		request.setAttribute("totalComputerFound", ComputerDTOServiceImpl.getInstance().getCount());
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
