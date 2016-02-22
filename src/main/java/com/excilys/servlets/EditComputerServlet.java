package com.excilys.servlets;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;
import com.excilys.service.impl.CompanyDtoServiceImpl;
import com.excilys.service.impl.ComputerDtoServiceImpl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class editComputer.
 */
public class EditComputerServlet extends HttpServlet {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new edits the computer servlet.
   *
   * @see HttpServlet#HttpServlet()
   */
  public EditComputerServlet() {
    super();
  }

  /**
   * Do get.
   *
   * @param request
   *          the request
   * @param response
   *          the response
   * @throws ServletException
   *           the servlet exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String computerId = request.getParameter("computer-id");

    if (computerId != null) {

      ComputerDto computerDto =
          ComputerDtoServiceImpl.getInstance().getById(Integer.parseInt(computerId));
      ArrayList<CompanyDto> companyDtoList = CompanyDtoServiceImpl.getInstance().listCompanies();

      request.setAttribute("computer", computerDto);
      request.setAttribute("companyList", companyDtoList);
      request.getRequestDispatcher("/WEB-INF/jsp/editComputer.jsp").forward(request, response);
    } else {
      response.sendRedirect("dashboard");
    }
  }

  /**
   * Do post.
   *
   * @param request
   *          the request
   * @param response
   *          the response
   * @throws ServletException
   *           the servlet exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String computerId = request.getParameter("computerId");
    String computerName = request.getParameter("computerName");
    String introduced = request.getParameter("introduced");
    String discontinued = request.getParameter("discontinued");
    String companyId = request.getParameter("companyId");

    ComputerDto computerDto = new ComputerDto(Integer.parseInt(computerId), computerName,
        introduced, discontinued, Integer.parseInt(companyId), "");

    ComputerDtoServiceImpl.getInstance().updateComputer(computerDto);

    response.sendRedirect("dashboard");
  }

}
