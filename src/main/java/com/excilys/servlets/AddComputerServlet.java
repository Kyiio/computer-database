package com.excilys.servlets;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;
import com.excilys.service.CompanyDtoService;
import com.excilys.service.ComputerDtoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddComputerServlet This class is used to manage the addComputer JSP
 * page, it shows the form to add a computer and make the insertion in the database by calling the
 * DTO service.
 * 
 * @author B. Herbaut
 */
public class AddComputerServlet extends HttpServlet {

  /** The Constant serialVersionUID. */
  private static final long  serialVersionUID = 1L;

  @Autowired
  private CompanyDtoService  companyDtoService;
  @Autowired
  private ComputerDtoService computerDtoService;

  /**
   * Instantiates a new adds the computer servlet.
   *
   * @see HttpServlet#HttpServlet()
   */
  public AddComputerServlet() {
    super();
  }

  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /**
   * Do get.
   *
   * @param request the request
   * @param response the response
   * @throws ServletException the servlet exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // We give the company list to the addComputer JSP so that the user can
    // choose witch company to associate to his computer
    ArrayList<CompanyDto> companyDtoList = companyDtoService.listCompanies();

    request.setAttribute("companyList", companyDtoList);
    request.getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request, response);
  }

  /**
   * Do post.
   *
   * @param request the request
   * @param response the response
   * @throws ServletException the servlet exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // We retrieve the information given by the user in the form
    String computerName = request.getParameter("computerName");
    String introduced = request.getParameter("introduced");
    String discontinued = request.getParameter("discontinued");
    String companyId = request.getParameter("companyId");

    ComputerDto computerDto =
        new ComputerDto(0, computerName, introduced, discontinued, Long.parseLong(companyId), "");

    computerDtoService.insertComputer(computerDto);

    response.sendRedirect("dashboard");
  }

}
