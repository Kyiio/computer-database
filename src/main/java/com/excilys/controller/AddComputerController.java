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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/add-computer")
public class AddComputerController {

  @Autowired
  private CompanyDtoService  companyDtoService;
  @Autowired
  private ComputerDtoService computerDtoService;

  /**
   * Show form.
   *
   * @param modelMap the model map
   * @return the string
   */
  @RequestMapping(method = RequestMethod.GET)
  public String showForm(ModelMap modelMap) {

    ArrayList<CompanyDto> companyDtoList = companyDtoService.listCompanies();

    modelMap.addAttribute("companyList", companyDtoList);

    return "addComputer";
  }

  /**
   * Perform add.
   *
   * @param computerDto the computer dto
   * @return the string
   */
  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView performAdd(ComputerDto computerDto) {

    computerDtoService.insertComputer(computerDto);

    return new ModelAndView("redirect:/dashboard");
  }

}
