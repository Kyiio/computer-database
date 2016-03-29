package com.excilys.webservice;

import com.excilys.dto.CompanyDto;
import com.excilys.service.CompanyDtoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/company")
public class CompanyWebService {

  @Autowired
  private CompanyDtoService companyDtoService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ArrayList<CompanyDto> list() {

    return companyDtoService.listCompanies();
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public String deleteCompany(@RequestBody long id) {

    String message = "The company has been deleted successfully";

    try {
      companyDtoService.deleteCompany(id);
    } catch (RuntimeException e) {
      message = "Error while trying to delete the company, retry later";
    }

    return message;
  }

  @RequestMapping(value = "/get/id", method = RequestMethod.GET)
  public CompanyDto get(long id) {

    return companyDtoService.getById(id);
  }

  @RequestMapping(value = "/get/name", method = RequestMethod.GET)
  public ArrayList<CompanyDto> get(String name) {

    return companyDtoService.getByName(name);
  }
}
