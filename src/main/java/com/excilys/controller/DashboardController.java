package com.excilys.controller;

import com.excilys.dto.ComputerDto;
import com.excilys.dto.PageDto;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerDtoService;
import com.excilys.servlets.util.PageCreator;
import com.excilys.servlets.util.QueryParametersBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping(value = { "/", "/dashboard" })
public class DashboardController {

  @Autowired
  private ComputerDtoService computerDtoService;

  @RequestMapping(method = RequestMethod.GET)
  public String showDashboard(PageDto pageDto, ModelMap modelMap) {

    // We build the query parameters from the data in the pageDto object
    QueryParameters queryParameters = QueryParametersBuilder.createQueryParameters(pageDto);

    // We retrieve the needed computers from the database
    ArrayList<ComputerDto> computerDtoList =
        computerDtoService.selectWithParameters(queryParameters);

    // We retrieve the number of computer in the database
    long computerCount = computerDtoService.getCount(queryParameters);

    pageDto = PageCreator.buildPage(queryParameters, computerDtoList, computerCount);
    modelMap.addAttribute("page", pageDto);

    return "dashboard";
  }

}
