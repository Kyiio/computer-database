package com.excilys.controller;

import com.excilys.controller.util.PageCreator;
import com.excilys.controller.util.QueryParametersBuilder;
import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;
import com.excilys.dto.PageDto;
import com.excilys.model.QueryParameters;
import com.excilys.service.CompanyDtoService;
import com.excilys.service.ComputerDtoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/computers")
public class ComputerController {

  private static final Logger   LOGGER = LoggerFactory.getLogger(ComputerController.class);

  @Autowired
  private CompanyDtoService     companyDtoService;
  @Autowired
  private ComputerDtoService    computerDtoService;

  private ArrayList<CompanyDto> companyDtoList;

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
   * Show form.
   *
   * @param computerId the computer id
   * @param model the model map
   * @return the string
   */
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String showEditForm(
      @RequestParam(value = "computerId", required = true, defaultValue = "-1") Long computerId,
      Model model) {

    LOGGER.info("Show edit form");

    ComputerDto computerDto = computerDtoService.getById(computerId);
    companyDtoList = companyDtoService.listCompanies();

    model.addAttribute("computerDto", computerDto);
    model.addAttribute("companyList", companyDtoList);

    return "editComputer";
  }

  /**
   * Perform update.
   *
   * @param computerDto the computer dto
   * @param bindingResult the binding result
   * @return the string
   */
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public String performUpdate(@Valid ComputerDto computerDto, BindingResult bindingResult,
      Model model) {

    LOGGER.info("Performing update");

    if (bindingResult.hasErrors()) {
      LOGGER.info("Error detected, return to the form");

      model.addAttribute("org.springframework.validation.BindingResult.computerDto", bindingResult);
      model.addAttribute("computerDto", computerDto);
      model.addAttribute("companyList", companyDtoList);

      return "editComputer";
    }

    computerDtoService.updateComputer(computerDto);

    return "redirect:/computers";
  }

  /**
   * Show form.
   *
   * @param model the model map
   * @return the string
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String showAddForm(Model model, String lang) {

    LOGGER.info("Show add form");

    companyDtoList = companyDtoService.listCompanies();

    model.addAttribute("computerDto", new ComputerDto());
    model.addAttribute("companyList", companyDtoList);

    return "addComputer";
  }

  /**
   * Perform add.
   *
   * @param computerDto the computer dto
   * @return the string
   */
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String performAdd(@Valid ComputerDto computerDto, BindingResult bindingResult,
      Model model) {

    LOGGER.info("Performing insertion");

    if (bindingResult.hasErrors()) {
      model.addAttribute("org.springframework.validation.BindingResult.computerDto", bindingResult);
      model.addAttribute("computerDto", computerDto);
      model.addAttribute("companyList", companyDtoList);

      return "addComputer";
    }

    computerDtoService.insertComputer(computerDto);

    return "redirect:/computers";
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
