package com.excilys.controller;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;
import com.excilys.service.CompanyDtoService;
import com.excilys.service.ComputerDtoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import javax.validation.Valid;

@Controller
@RequestMapping("/edit-computer")
public class EditComputerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);

  @Autowired
  private CompanyDtoService  companyDtoService;
  @Autowired
  private ComputerDtoService computerDtoService;

  private ArrayList<CompanyDto> companyDtoList;

  /**
   * Show form.
   *
   * @param computerId the computer id
   * @param model the model map
   * @return the string
   */
  @RequestMapping(method = RequestMethod.GET)
  public String showForm(
      @RequestParam(value = "computerId", required = true, defaultValue = "-1") Long computerId,
      Model model) {

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
  @RequestMapping(method = RequestMethod.POST)
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

    return "redirect:/dashboard";
  }

}
