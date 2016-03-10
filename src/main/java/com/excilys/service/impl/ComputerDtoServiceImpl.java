package com.excilys.service.impl;

import com.excilys.dto.ComputerDto;
import com.excilys.dto.mapper.ComputerDtoMapper;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerDtoService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.QueryParametersValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ComputerDtoServiceImpl implements ComputerDtoService {

  @Autowired
  public ComputerService computerService;

  @Override
  public void updateComputer(ComputerDto computerDto) {

    ComputerValidator.checkId(computerDto.getComputerId());
    ComputerValidator.checkName(computerDto.getComputerName());
    ComputerValidator.checkDate(computerDto.getIntroducedDate());
    ComputerValidator.checkDate(computerDto.getDiscontinuedDate());

    Computer computer = ComputerDtoMapper.getComputer(computerDto);

    computerService.updateComputer(computer);
  }

  @Override
  public long insertComputer(ComputerDto computerDto) {

    ComputerValidator.checkName(computerDto.getComputerName());
    ComputerValidator.checkDate(computerDto.getIntroducedDate());
    ComputerValidator.checkDate(computerDto.getDiscontinuedDate());

    Computer computer = ComputerDtoMapper.getComputer(computerDto);

    return computerService.insertComputer(computer.getCompany(), computer.getIntroduced(),
        computer.getDiscontinued(), computer.getName());
  }

  @Override
  public void deleteComputer(long id) {

    ComputerValidator.checkId(id);
    computerService.deleteComputer(id);
  }

  @Override
  public ComputerDto getById(long id) {

    ComputerValidator.checkId(id);
    Computer computer = computerService.getById(id);
    return ComputerDtoMapper.getComputerDto(computer);
  }

  @Override
  public ArrayList<ComputerDto> getByName(String name) {

    ComputerValidator.checkName(name);
    ArrayList<Computer> computerList = computerService.getByName(name);
    return ComputerDtoMapper.getComputerDtoList(computerList);
  }

  @Override
  public ArrayList<ComputerDto> listComputers() {

    ArrayList<Computer> computerList = computerService.listComputers();
    return ComputerDtoMapper.getComputerDtoList(computerList);
  }

  @Override
  public ArrayList<ComputerDto> selectWithParameters(QueryParameters queryParameters) {

    QueryParametersValidator.validateQueryParameters(queryParameters);

    ArrayList<Computer> computerList = computerService.selectWithParameters(queryParameters);

    return ComputerDtoMapper.getComputerDtoList(computerList);
  }

  @Override
  public long getCount(QueryParameters queryParameters) {

    return computerService.getCount(queryParameters);
  }

}
