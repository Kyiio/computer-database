package com.excilys.dto.mapper;

import java.util.ArrayList;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

/**
 * Interface that offers methods to map from a Company to a CompanyDTO and
 * from a CompanyDTO to a Company.
 * 
 * @author B. Herbaut
 * @see Company, CompanyDTO
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