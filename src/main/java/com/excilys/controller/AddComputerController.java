package com.excilys.controller;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;
import com.excilys.service.CompanyDtoService;
import com.excilys.service.ComputerDtoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

import javax.validation.Valid;

@Controller
@RequestMapping("/add-computer")
public class AddComputerController {

  @Autowired
  private CompanyDtoService     companyDtoService;
  @Autowired
  private ComputerDtoService    computerDtoService;

  private ArrayList<CompanyDto> companyDtoList;
  private String                lang;

  /**
   * Show form.
   *
   * @param model the model map
   * @return the string
   */
  @RequestMapping(method = RequestMethod.GET)
  public String showForm(Model model, String lang) {

    companyDtoList = companyDtoService.listCompanies();

    model.addAttribute("computerDto", new ComputerDto());
    model.addAttribute("companyList", companyDtoList);
    this.lang = lang;
    model.addAttribute("lang", lang);

    return "addComputer";
  }

  /**
   * Perform add.
   *
   * @param computerDto the computer dto
   * @return the string
   */
  @RequestMapping(method = RequestMethod.POST)
  public String performAdd(@Valid ComputerDto computerDto, BindingResult bindingResult,
      Model model) {

    model.addAttribute("lang", lang);

    if (bindingResult.hasErrors()) {
      model.addAttribute("org.springframework.validation.BindingResult.computerDto", bindingResult);
      model.addAttribute("computerDto", computerDto);
      model.addAttribute("companyList", companyDtoList);

      return "addComputer";
    }

    computerDtoService.insertComputer(computerDto);

    return "redirect:/dashboard";
  }

}
