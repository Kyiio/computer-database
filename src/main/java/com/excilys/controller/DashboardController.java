package com.excilys.controller;

import com.excilys.controller.util.PageCreator;
import com.excilys.controller.util.QueryParametersBuilder;
import com.excilys.dto.ComputerDto;
import com.excilys.dto.PageDto;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerDtoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping(value = { "/", "/dashboard" })
public class DashboardController {

  private static final Logger LOGGER = LoggerFactory.getLogger(QueryParametersBuilder.class);

  @Autowired
  private ComputerDtoService  computerDtoService;

  /**
   * Show dashboard.
   *
   * @param pageDto The page dto
   * @param modelMap The model map
   * @return The name of the jsp page to show
   */
  @RequestMapping(method = RequestMethod.GET)
  public String showDashboard(PageDto pageDto, ModelMap modelMap) {

    LOGGER.info("Show dashboard");

    pageDto = getPageDtoFromPreviousOne(pageDto);
    modelMap.addAttribute("page", pageDto);

    return "dashboard";
  }

  /**
   * Delete computer.
   *
   * @param pageDto the page dto
   * @param selection the selection
   * @param modelMap the model map
   * @return the string
   */
  @RequestMapping(method = RequestMethod.POST)
  public String deleteComputer(PageDto pageDto, Long[] selection, ModelMap modelMap) {

    LOGGER.info("User tries to delete computers");

    for (int i = 0; i < selection.length; i++) {
      computerDtoService.deleteComputer(selection[i]);
    }

    pageDto = getPageDtoFromPreviousOne(pageDto);
    modelMap.addAttribute("page", pageDto);

    return "dashboard";
  }

  /**
   * Update page dto.
   *
   * @param pageDto the page dto
   * @return the page dto from previous one
   */
  public PageDto getPageDtoFromPreviousOne(PageDto pageDto) {

    // We build the query parameters from the data in the pageDto object
    QueryParameters queryParameters = QueryParametersBuilder.createQueryParameters(pageDto);

    // We retrieve the needed computers from the database
    ArrayList<ComputerDto> computerDtoList =
        computerDtoService.selectWithParameters(queryParameters);

    // We retrieve the number of computer in the database
    long computerCount = computerDtoService.getCount(queryParameters);

    pageDto = PageCreator.buildPage(queryParameters, computerDtoList, computerCount);

    return pageDto;
  }
}
