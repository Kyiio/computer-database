package com.excilys.dto.mapper;

import com.excilys.dto.ComputerDto;
import com.excilys.exception.MappingException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyDtoService;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Interface that offers methods to map from a Computer to a ComputerDTO and from a ComputerDTO to a
 * Computer.
 * 
 * @author B. Herbaut
 * @see Computer, ComputerDTO
 */
@Component
public class ComputerDtoMapper {


  private static CompanyDtoService companyDtoService;
  private static MessageSource     messageSource;

  static {
    try (ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("applicationContext.xml")) {

      companyDtoService = applicationContext.getBean("companyDtoService", CompanyDtoService.class);
      messageSource = applicationContext.getBean("messageSource", MessageSource.class);
    }
  }

  /**
   * Method that maps an ArrayList of Computer into an ArrayList of ComputerDTO.
   * 
   * @param computerList The Computer ArrayList that will be mapped
   * @return The ComputerDTO ArrayList
   */
  public static ArrayList<ComputerDto> getComputerDtoList(ArrayList<Computer> computerList) {

    ArrayList<ComputerDto> computerDtoList = new ArrayList<>();

    for (Computer computer : computerList) {
      computerDtoList.add(getComputerDto(computer));
    }

    return computerDtoList;
  }

  /**
   * Method that maps a Computer into a ComputerDTO.
   * 
   * @param computer The computer that will be mapped.
   * @return The mapped ComputerDTO.
   */
  public static ComputerDto getComputerDto(Computer computer) {

    String introducedString = "";
    String discontinuedString = "";
    String companyName = "";
    long companyId = -1;

    Company company = computer.getCompany();

    LocalDate introducedDate = computer.getIntroduced();
    LocalDate discontinuedDate = computer.getDiscontinued();
    
    
    String format = messageSource.getMessage("date.format", null, LocaleContextHolder.getLocale());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

    if (introducedDate != null) {
      LocalDateTime ldt = LocalDateTime.of(introducedDate, LocalTime.of(0, 0));
      introducedString = ldt.format(formatter);
    }

    if (discontinuedDate != null) {
      LocalDateTime ldt2 = LocalDateTime.of(discontinuedDate, LocalTime.of(0, 0));
      discontinuedString = ldt2.format(formatter);
    }

    if (company != null) {
      companyName = company.getName();
      companyId = company.getId();
    }

    return new ComputerDto(computer.getId(), computer.getName(), introducedString,
        discontinuedString, companyId, companyName);
  }

  /**
   * Method that maps a ComputerDTO into a Computer.
   * 
   * @param computerDto The ComputerDTO that will be mapped.
   * @return The mapped Computer.
   */
  public static Computer getComputer(ComputerDto computerDto) {
    return getComputer(computerDto.getComputerId(), computerDto.getComputerName(),
        computerDto.getIntroducedDate(), computerDto.getDiscontinuedDate(),
        computerDto.getCompanyId());
  }

  /**
   * Method that maps the content of a ComputerDTO into a Computer.
   * 
   * @param computerId The id of the computer
   * @param computerName The name of the computer
   * @param introduced The introduced date
   * @param discontinued The discontinued date
   * @param companyId The company id
   * @return The mapped Computer.
   */
  public static Computer getComputer(long computerId, String computerName, String introduced,
      String discontinued, long companyId) {

    String format = messageSource.getMessage("date.format", null, LocaleContextHolder.getLocale());
    
    LocalDate introducedDate = null;
    LocalDate discontinuedDate = null;

    if (introduced != null && introduced.length() > 0) {
      try {
        introducedDate = new Timestamp(new SimpleDateFormat(format).parse(introduced).getTime())
            .toLocalDateTime().toLocalDate();
      } catch (ParseException e) {
        throw new MappingException(
            "The given introduced date isn't matching the format : " + introduced);
      }
    }

    if (discontinued != null && discontinued.length() > 0) {
      try {
        discontinuedDate = new Timestamp(new SimpleDateFormat(format).parse(discontinued).getTime())
            .toLocalDateTime().toLocalDate();
      } catch (ParseException e) {
        throw new MappingException(
            "The given discontinued date isn't matching the format : " + discontinued);
      }
    }

    Company company = null;

    if (companyId > 0) {
      company = CompanyDtoMapper.getCompany(companyDtoService.getById(companyId));
    }

    return new Computer(computerId, company, computerName, discontinuedDate, introducedDate);
  }

}
