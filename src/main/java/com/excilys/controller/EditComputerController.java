package com.excilys.controller;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;
import com.excilys.service.CompanyDtoService;
import com.excilys.service.ComputerDtoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/edit-computer")
public class EditComputerController {

  @Autowired
  private CompanyDtoService  companyDtoService;
  @Autowired
  private ComputerDtoService computerDtoService;

  /**
   * Show form.
   *
   * @param computerId the computer id
   * @param modelMap the model map
   * @return the string
   */
  @RequestMapping(method = RequestMethod.GET)
  public String showForm(
      @RequestParam(value = "computerId", required = true, defaultValue = "-1") Long computerId,
      ModelMap modelMap) {

    ComputerDto computerDto = computerDtoService.getById(computerId);
    ArrayList<CompanyDto> companyDtoList = companyDtoService.listCompanies();

    modelMap.addAttribute("computer", computerDto);
    modelMap.addAttribute("companyList", companyDtoList);

    return "editComputer";
  }

  /**
   * Perform update.
   *
   * @param computerDto the computer dto
   * @param modelMap the model map
   * @return the string
   */
  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView performUpdate(ComputerDto computerDto, ModelMap modelMap) {

    computerDtoService.updateComputer(computerDto);

    return new ModelAndView("redirect:/dashboard");
  }

}
