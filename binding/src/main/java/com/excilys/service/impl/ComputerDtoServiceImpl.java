package com.excilys.service.impl;

import com.excilys.dto.ComputerDto;
import com.excilys.dto.mapper.ComputerDtoMapper;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerDtoService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.QueryParametersValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ComputerDtoServiceImpl implements ComputerDtoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDtoServiceImpl.class);

  @Autowired
  public ComputerService      computerService;

  @Autowired
  public ComputerDtoMapper    computerDtoMapper;

  @Autowired
  public ComputerValidator    computerValidator;

  @Override
  public void updateComputer(ComputerDto computerDto) {

    LOGGER.info("DTO : Update computer : " + computerDto);

    computerValidator.checkId(computerDto.getComputerId());
    computerValidator.checkName(computerDto.getComputerName());
    computerValidator.checkDate(computerDto.getIntroducedDate());
    computerValidator.checkDate(computerDto.getDiscontinuedDate());

    Computer computer = computerDtoMapper.getComputer(computerDto);

    computerService.updateComputer(computer);
  }

  @Override
  public long insertComputer(ComputerDto computerDto) {

    LOGGER.info("DTO : Insert computer : " + computerDto);

    computerValidator.checkName(computerDto.getComputerName());
    computerValidator.checkDate(computerDto.getIntroducedDate());
    computerValidator.checkDate(computerDto.getDiscontinuedDate());

    Computer computer = computerDtoMapper.getComputer(computerDto);

    return computerService.insertComputer(computer.getCompany(), computer.getIntroduced(),
        computer.getDiscontinued(), computer.getName());
  }

  @Override
  public void deleteComputer(long id) {

    LOGGER.info("DTO : Delete computer by id: " + id);

    computerValidator.checkId(id);
    computerService.deleteComputer(id);
  }

  @Override
  public ComputerDto getById(long id) {

    LOGGER.info("DTO : Get computer by id: " + id);

    computerValidator.checkId(id);
    Computer computer = computerService.getById(id);
    return computerDtoMapper.getComputerDto(computer);
  }

  @Override
  public ArrayList<ComputerDto> getByName(String name) {

    LOGGER.info("DTO : Get computer by name: " + name);

    computerValidator.checkName(name);
    ArrayList<Computer> computerList = computerService.getByName(name);
    return computerDtoMapper.getComputerDtoList(computerList);
  }

  @Override
  public ArrayList<ComputerDto> listComputers() {

    LOGGER.info("DTO : Get list computers");

    ArrayList<Computer> computerList = computerService.listComputers();
    return computerDtoMapper.getComputerDtoList(computerList);
  }

  @Override
  public ArrayList<ComputerDto> selectWithParameters(QueryParameters queryParameters) {

    LOGGER.info("DTO : selectWithParameters, parameters : " + queryParameters);

    QueryParametersValidator.validateQueryParameters(queryParameters);

    ArrayList<Computer> computerList = computerService.selectWithParameters(queryParameters);

    return computerDtoMapper.getComputerDtoList(computerList);
  }

  @Override
  public long getCount(QueryParameters queryParameters) {

    LOGGER.info("DTO : getCount query, parameters : " + queryParameters);

    return computerService.getCount(queryParameters);
  }

}
