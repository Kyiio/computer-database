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
 * Servlet implementation class editComputer
 */
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String computerId = request.getParameter("computer-id");

		if (computerId != null) {

			ComputerDTO computerDTO = ComputerDTOServiceImpl.getInstance().getById(Integer.parseInt(computerId));
			ArrayList<CompanyDTO> companyDTOList = CompanyDTOServiceImpl.getInstance().listCompanies();

			request.setAttribute("computer", computerDTO);
			request.setAttribute("companyList", companyDTOList);
			request.getRequestDispatcher("/WEB-INF/jsp/editComputer.jsp").forward(request, response);
		} else {
			response.sendRedirect("dashboard");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String computerId = request.getParameter("computerId");
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		ComputerDTO computerDTO = new ComputerDTO(Integer.parseInt(computerId), computerName, introduced, discontinued,
				Integer.parseInt(companyId), "");

		ComputerDTOServiceImpl.getInstance().updateComputer(computerDTO);
		
		response.sendRedirect("dashboard");
	}

}
