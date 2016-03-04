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
 * Servlet implementation class editComputer.
 */
public class EditComputerServlet extends HttpServlet {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  @Autowired
  private CompanyDtoService  companyDtoService;
  @Autowired
  private ComputerDtoService computerDtoService;

  /**
   * Instantiates a new edits the computer servlet.
   *
   * @see HttpServlet#HttpServlet()
   */
  public EditComputerServlet() {
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

      ComputerDto computerDto = computerDtoService.getById(Long.parseLong(computerId));
      ArrayList<CompanyDto> companyDtoList = companyDtoService.listCompanies();

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

    ComputerDto computerDto = new ComputerDto(Long.parseLong(computerId), computerName, introduced,
        discontinued, Long.parseLong(companyId), "");

    computerDtoService.updateComputer(computerDto);

    response.sendRedirect("dashboard");
  }

}
