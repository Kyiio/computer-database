package com.excilys.service.impl;

import java.util.ArrayList;

import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.DTOMapper;
import com.excilys.model.Computer;
import com.excilys.service.ComputerDTOService;
import com.excilys.validator.ComputerValidator;

public class ComputerDTOServiceImpl implements ComputerDTOService{

	public static ComputerDTOService INSTANCE;

	static 
	{
		INSTANCE = new ComputerDTOServiceImpl();
	}
	
	public static ComputerDTOService getInstance(){
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
		
		Computer computer = DTOMapper.getComputerFromDTO(computerDTO);
		
		ComputerServiceImpl.getInstance().updateComputer(computer);
	}

	@Override
	public int insertComputer(ComputerDTO computerDTO) {
		
		ComputerValidator.checkName(computerDTO.getComputerName());
		ComputerValidator.checkDate(computerDTO.getIntroducedDate(), null);
		ComputerValidator.checkDate(computerDTO.getDiscontinuedDate(), null);
		
		Computer computer = DTOMapper.getComputerFromDTO(computerDTO);
		
		return ComputerServiceImpl.getInstance().insertComputer(computer.getCompany(), computer.getIntroduced(), computer.getDiscontinued(), computer.getName());
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
		return DTOMapper.getComputerDTOFromComputer(computer);
	}

	@Override
	public ArrayList<ComputerDTO> getByName(String name) {
		
		ComputerValidator.checkName(name);
		ArrayList<Computer> computerList = ComputerServiceImpl.getInstance().getByName(name);
		return DTOMapper.getComputerDTOListFromComputerList(computerList);
	}

	@Override
	public ArrayList<ComputerDTO> listComputers() {
		
		ArrayList<Computer> computerList = ComputerServiceImpl.getInstance().listComputers();
		return DTOMapper.getComputerDTOListFromComputerList(computerList);
	}

	@Override
	public ArrayList<ComputerDTO> getXComputersStartingAtIndexY(int offset, int pageNumber) {

		ComputerValidator.checkOffset(offset);
		ComputerValidator.checkPageNumber(pageNumber);
		
		return DTOMapper.getComputerDTOListFromComputerList(ComputerServiceImpl.getInstance().getXComputersStartingAtIndexY(offset, pageNumber));
	}
	
	@Override
	public int getNbComputer() {
		return ComputerServiceImpl.getInstance().getNbComputer();
	}

}
