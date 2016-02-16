package com.excilys.mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.dao.impl.CompanyDAOImpl;
import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.MappingException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public interface DTOMapper {

	public static ArrayList<CompanyDTO> getCompanyDTOListFromCompanyList(ArrayList<Company> companyList) {

		ArrayList<CompanyDTO> companyDTOList = new ArrayList<>();

		for (Company company : companyList) {
			companyDTOList.add(new CompanyDTO(company));
		}

		return companyDTOList;
	}

	public static ArrayList<ComputerDTO> getComputerDTOListFromComputerList(ArrayList<Computer> computerList) {

		ArrayList<ComputerDTO> computerDTOList = new ArrayList<>();

		for (Computer computer : computerList) {
			computerDTOList.add(getComputerDTOFromComputer(computer));
		}

		return computerDTOList;
	}

	public static ComputerDTO getComputerDTOFromComputer(Computer computer){
	
		String introducedString = "";
		String discontinuedString = "";
		String companyName = "";
		int companyId = -1;

		Company company = computer.getCompany();

		LocalDateTime introducedDate = computer.getIntroduced();
		LocalDateTime discontinuedDate = computer.getDiscontinued();

		if (introducedDate != null) {
			introducedString = introducedDate.toString();
		}

		if (discontinuedDate != null) {
			discontinuedString = discontinuedDate.toString();
		}

		if (company != null) {
			companyName = company.getName();
			companyId = company.getId();
		}

		return new ComputerDTO(computer.getId(), computer.getName(), introducedString, discontinuedString, companyId, companyName);	
	}
	
	public static Computer getComputerFromDTO(ComputerDTO computerDTO){
		return getComputerFromDTO(computerDTO.getComputerId(), computerDTO.getComputerName(), computerDTO.getIntroducedDate(), computerDTO.getDiscontinuedDate(), computerDTO.getCompanyId());
	}
	
	public static Computer getComputerFromDTO(int computerId, String computerName, String introduced, String discontinued, int companyId) {
		
		String format = "yyyy-MM-dd";
		LocalDateTime introducedDate = null;
		LocalDateTime discontinuedDate = null;
		
		if(introduced != null && introduced.length() > 0){
			try {
				introducedDate = new Timestamp(new SimpleDateFormat(format).parse(introduced).getTime()).toLocalDateTime();
			} catch (ParseException e) {
				throw new MappingException("The given introduced date isn't matching the format : " + introduced);
			}
		}
		
		if(discontinued != null && discontinued.length() > 0){
			try {
				discontinuedDate = new Timestamp(new SimpleDateFormat(format).parse(discontinued).getTime()).toLocalDateTime();
			} catch (ParseException e) {
				throw new MappingException("The given discontinued date isn't matching the format : " + discontinued);
			}
		}
		
		Company company = null;
		
		if(companyId > 0){
			company = CompanyDAOImpl.getInstance().getById(companyId);
		}
		
		return new Computer(computerId, company, computerName, discontinuedDate, introducedDate);
	}
}
