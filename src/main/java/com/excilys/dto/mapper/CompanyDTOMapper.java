package com.excilys.dto.mapper;

import java.util.ArrayList;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

/**
 * 
 * Class that offer method to map a company into companyDTO
 * 
 * @author B. Herbaut 
 *
 */
public interface CompanyDTOMapper {

	public static ArrayList<CompanyDTO> getCompanyDTOList(ArrayList<Company> companyList) {

		ArrayList<CompanyDTO> companyDTOList = new ArrayList<>();

		for (Company company : companyList) {
			companyDTOList.add(getCompanyDTO(company));
		}

		return companyDTOList;
	}

	public static ArrayList<Company> getCompanyList(ArrayList<CompanyDTO> companyDTOList) {

		ArrayList<Company> companyList = new ArrayList<>();

		for (CompanyDTO companyDTO : companyDTOList) {
			companyList.add(getCompany(companyDTO));
		}

		return companyList;
	}
	
	public static CompanyDTO getCompanyDTO(Company company){
		
		return new CompanyDTO(company.getId(), company.getName());
	}
	
	public static Company getCompany(CompanyDTO companyDTO){
		
		return new Company(companyDTO.getId(), companyDTO.getName());
	}
}
