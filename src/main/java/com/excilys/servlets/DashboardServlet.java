package com.excilys.servlets;

import com.excilys.dto.ComputerDto;
import com.excilys.dto.PageDto;
import com.excilys.model.QueryParameters;
import com.excilys.service.impl.ComputerDtoServiceImpl;
import com.excilys.servlets.util.PageCreator;
import com.excilys.servlets.util.QueryParametersBuilder;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DashboardServlet. This class shows the list of all the computer in
 * pages. It also manage the deletion of computer.
 * 
 * @author B. Herbaut
 */
public class DashboardServlet extends HttpServlet {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public DashboardServlet() {

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

    // We build the query parameters by retrieving the data in the url
    QueryParameters queryParameters = QueryParametersBuilder.createQueryParameters(request);

    // We retrieve the needed computers from the database
    ArrayList<ComputerDto> computerDtoList =
        ComputerDtoServiceImpl.getInstance().selectWithParameters(queryParameters);

    // We retrieve the number of computer in the database
    int computerCount = ComputerDtoServiceImpl.getInstance().getCount(queryParameters);

    PageDto page = PageCreator.buildPage(queryParameters, computerDtoList, computerCount);

    // Finally we send all this information to the JSP page
    request.setAttribute("page", page);
    request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
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

    String idsString = request.getParameter("selection");

    String[] idStrTab = idsString.split(",");

    for (int i = 0; i < idStrTab.length; ++i) {
      ComputerDtoServiceImpl.getInstance().deleteComputer(Integer.parseInt(idStrTab[i]));
    }

    // response.sendRedirect("dashboard");
    doGet(request, response);
  }

}
