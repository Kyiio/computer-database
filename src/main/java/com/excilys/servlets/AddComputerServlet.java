package com.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.impl.CompanyDTOServiceImpl;
import com.excilys.service.impl.ComputerDTOServiceImpl;

/**
 * Servlet implementation class AddComputerServlet
 * 
 * This class is used to manage the addComputer JSP page, it shows the form to
 * add a computer and make the insertion in the database by calling the DTO
 * service.
 * 
 * @author B. Herbaut
 */
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// We give the company list to the addComputer JSP so that the user can
		// choose witch company to associate to his computer
		ArrayList<CompanyDTO> companyDTOList = CompanyDTOServiceImpl.getInstance().listCompanies();

		request.setAttribute("companyList", companyDTOList);
		request.getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

		// We retrieve the information given by the user in the form
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");

		ComputerDTO computerDTO = new ComputerDTO(0, computerName, introduced, discontinued,
				Integer.parseInt(companyId), "");

		ComputerDTOServiceImpl.getInstance().insertComputer(computerDTO);
	}

}
