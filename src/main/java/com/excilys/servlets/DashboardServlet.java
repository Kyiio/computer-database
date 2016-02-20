package com.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.PageDTO;
import com.excilys.model.QueryParameters;
import com.excilys.service.impl.ComputerDTOServiceImpl;
import com.excilys.servlets.util.PageCreator;
import com.excilys.servlets.util.QueryParametersBuilder;

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

		// We build the query parameters by retrieving the data in the url
		QueryParameters queryParameters = QueryParametersBuilder.createQueryParameters(request);

		// We retrieve the needed computers from the database
		ArrayList<ComputerDTO> computerDTOList = ComputerDTOServiceImpl.getInstance()
				.selectWithParameters(queryParameters);

		// We retrieve the number of computer in the database
		int computerCount = ComputerDTOServiceImpl.getInstance().getCount(queryParameters);

		PageDTO page = PageCreator.buildPage(queryParameters, computerDTOList, computerCount);

		// Finally we send all this information to the JSP page
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idsString = request.getParameter("selection");

		String[] idStrTab = idsString.split(",");

		for (int i = 0; i < idStrTab.length; ++i) {
			ComputerDTOServiceImpl.getInstance().deleteComputer(Integer.parseInt(idStrTab[i]));
		}

		response.sendRedirect("dashboard");
	}

}
