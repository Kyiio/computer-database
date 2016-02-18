package com.excilys.service.impl;

import java.util.ArrayList;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.mapper.ComputerDTOMapper;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerDTOService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.QueryParametersValidator;

public class ComputerDTOServiceImpl implements ComputerDTOService {

	public static ComputerDTOService INSTANCE;

	static {
		INSTANCE = new ComputerDTOServiceImpl();
	}

	public static ComputerDTOService getInstance() {
		return INSTANCE;
	}

	private ComputerDTOServiceImpl() {

	}

	@Override
	public void updateComputer(ComputerDTO computerDTO) {

		ComputerValidator.checkId(computerDTO.getComputerId());
		ComputerValidator.checkName(computerDTO.getComputerName());
		ComputerValidator.checkDate(computerDTO.getIntroducedDate(), null);
		ComputerValidator.checkDate(computerDTO.getDiscontinuedDate(), null);

		Computer computer = ComputerDTOMapper.getComputer(computerDTO);

		ComputerServiceImpl.getInstance().updateComputer(computer);
	}

	@Override
	public int insertComputer(ComputerDTO computerDTO) {

		ComputerValidator.checkName(computerDTO.getComputerName());
		ComputerValidator.checkDate(computerDTO.getIntroducedDate(), null);
		ComputerValidator.checkDate(computerDTO.getDiscontinuedDate(), null);

		Computer computer = ComputerDTOMapper.getComputer(computerDTO);

		return ComputerServiceImpl.getInstance().insertComputer(computer.getCompany(), computer.getIntroduced(),
				computer.getDiscontinued(), computer.getName());
	}

	@Override
	public void deleteComputer(int id) {

		ComputerValidator.checkId(id);
		ComputerServiceImpl.getInstance().deleteComputer(id);
	}

	@Override
	public ComputerDTO getById(int id) {

		ComputerValidator.checkId(id);
		Computer computer = ComputerServiceImpl.getInstance().getById(id);
		return ComputerDTOMapper.getComputerDTO(computer);
	}

	@Override
	public ArrayList<ComputerDTO> getByName(String name) {

		ComputerValidator.checkName(name);
		ArrayList<Computer> computerList = ComputerServiceImpl.getInstance().getByName(name);
		return ComputerDTOMapper.getComputerDTOList(computerList);
	}

	@Override
	public ArrayList<ComputerDTO> listComputers() {

		ArrayList<Computer> computerList = ComputerServiceImpl.getInstance().listComputers();
		return ComputerDTOMapper.getComputerDTOList(computerList);
	}

	@Override
	public ArrayList<ComputerDTO> selectWithParameters(QueryParameters queryParameters) {

		QueryParametersValidator.validateQueryParameters(queryParameters);

		ArrayList<Computer> computerList = ComputerServiceImpl.getInstance().selectWithParameters(queryParameters);

		return ComputerDTOMapper.getComputerDTOList(computerList);
	}

	@Override
	public int getCount() {

		return ComputerServiceImpl.getInstance().getCount();
	}

}
