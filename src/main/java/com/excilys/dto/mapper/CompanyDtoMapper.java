package com.excilys.dto.mapper;

import com.excilys.dto.CompanyDto;
import com.excilys.model.Company;

import java.util.ArrayList;

/**
 * Interface that offers methods to map from a Company to a CompanyDTO and from a CompanyDTO to a
 * Company.
 * 
 * @author B. Herbaut
 * @see Company, CompanyDTO
 */
public interface CompanyDtoMapper {

  /**
   * Gets the company dto list.
   *
   * @param companyList
   *          The company list
   * @return The company dto list
   */
  public static ArrayList<CompanyDto> getCompanyDtoList(ArrayList<Company> companyList) {

    ArrayList<CompanyDto> companyDtoList = new ArrayList<>();

    for (Company company : companyList) {
      companyDtoList.add(getCompanyDto(company));
    }

    return companyDtoList;
  }

  /**
   * Gets the company list.
   *
   * @param companyDtoList
   *          The company dto list
   * @return The company list
   */
  public static ArrayList<Company> getCompanyList(ArrayList<CompanyDto> companyDtoList) {

    ArrayList<Company> companyList = new ArrayList<>();

    for (CompanyDto companyDto : companyDtoList) {
      companyList.add(getCompany(companyDto));
    }

    return companyList;
  }

  /**
   * Gets the company dto.
   *
   * @param company
   *          The company
   * @return The company dto
   */
  public static CompanyDto getCompanyDto(Company company) {

    return new CompanyDto(company.getId(), company.getName());
  }

  /**
   * Gets the company.
   *
   * @param companyDto
   *          The company dto
   * @return The company
   */
  public static Company getCompany(CompanyDto companyDto) {

    return new Company(companyDto.getId(), companyDto.getName());
  }
}
