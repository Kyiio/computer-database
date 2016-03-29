package com.excilys.webservice;

import com.excilys.dto.ComputerDto;
import com.excilys.dto.PageDto;
import com.excilys.dto.util.PageCreator;
import com.excilys.service.ComputerDtoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/computer")
public class ComputerWebService {

  @Autowired
  private ComputerDtoService computerDtoService;

  @Autowired
  private PageCreator        pageCreator;

  @RequestMapping(value = "/page", method = RequestMethod.GET)
  public PageDto getPage(PageDto pageDto) {

    pageDto = pageCreator.getPageDtoFromPreviousOne(pageDto);

    return pageDto;
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public String updateComputer(@Valid @RequestBody ComputerDto computerDto) {

    String message = "Computer successfully updated";

    try {
      computerDtoService.updateComputer(computerDto);
    } catch (RuntimeException e) {
      message = "Error while trying to update the computer, retry later";
    }

    return message;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String insertComputer(@Valid @RequestBody ComputerDto computerDto) {

    String message = "Computer successfully inserted";

    try {
      long id = computerDtoService.insertComputer(computerDto);
      message += " with id : " + id;

    } catch (RuntimeException e) {
      message = "Error while trying to insert the computer, retry later";
    }

    return message;
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public String deleteComputer(@RequestBody long id) {

    String message = "Computer successfully deleted";

    try {
      computerDtoService.deleteComputer(id);

    } catch (RuntimeException e) {
      message = "Error while trying to delete the computer, retry later";
    }

    return message;
  }

  @RequestMapping(value = "/get/id", method = RequestMethod.GET)
  public ComputerDto get(long id) {
    return computerDtoService.getById(id);
  }

  @RequestMapping(value = "/get/name", method = RequestMethod.GET)
  public ArrayList<ComputerDto> get(String name) {
    return computerDtoService.getByName(name);
  }

}
