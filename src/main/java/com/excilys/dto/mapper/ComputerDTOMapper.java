package com.excilys.dto.mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.dao.impl.CompanyDAOImpl;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.MappingException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * Interface that offers methods to map from a Computer to a ComputerDTO and
 * from a ComputerDTO to a Computer.
 * 
 * @author B. Herbaut
 * @see Computer, ComputerDTO
 */
public interface ComputerDTOMapper {

	/**
	 * Method that maps an ArrayList of Computer into an ArrayList of
	 * ComputerDTO.
	 * 
	 * @param computerList
	 *            The Computer ArrayList that will be mapped
	 * @return The ComputerDTO ArrayList
	 */
	public static ArrayList<ComputerDTO> getComputerDTOList(ArrayList<Computer> computerList) {

		ArrayList<ComputerDTO> computerDTOList = new ArrayList<>();

		for (Computer computer : computerList) {
			computerDTOList.add(getComputerDTO(computer));
		}

		return computerDTOList;
	}

	/**
	 * Method that maps a Computer into a ComputerDTO.
	 * 
	 * @param computer
	 *            The computer that will be mapped.
	 * @return The mapped ComputerDTO.
	 */
	public static ComputerDTO getComputerDTO(Computer computer) {

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

		return new ComputerDTO(computer.getId(), computer.getName(), introducedString, discontinuedString, companyId,
				companyName);
	}

	/**
	 * Method that maps a ComputerDTO into a Computer.
	 * 
	 * @param computerDTO
	 *            The ComputerDTO that will be mapped.
	 * @return The mapped Computer.
	 */
	public static Computer getComputer(ComputerDTO computerDTO) {
		return getComputer(computerDTO.getComputerId(), computerDTO.getComputerName(), computerDTO.getIntroducedDate(),
				computerDTO.getDiscontinuedDate(), computerDTO.getCompanyId());
	}

	/**
	 * Method that maps the content of a ComputerDTO into a Computer.
	 * 
	 * @param computerId
	 * @param computerName
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 * @return The mapped Computer.
	 */
	public static Computer getComputer(int computerId, String computerName, String introduced, String discontinued,
			int companyId) {

		String format = "yyyy-MM-dd";
		LocalDateTime introducedDate = null;
		LocalDateTime discontinuedDate = null;

		if (introduced != null && introduced.length() > 0) {
			try {
				introducedDate = new Timestamp(new SimpleDateFormat(format).parse(introduced).getTime())
						.toLocalDateTime();
			} catch (ParseException e) {
				throw new MappingException("The given introduced date isn't matching the format : " + introduced);
			}
		}

		if (discontinued != null && discontinued.length() > 0) {
			try {
				discontinuedDate = new Timestamp(new SimpleDateFormat(format).parse(discontinued).getTime())
						.toLocalDateTime();
			} catch (ParseException e) {
				throw new MappingException("The given discontinued date isn't matching the format : " + discontinued);
			}
		}

		Company company = null;

		if (companyId > 0) {
			company = CompanyDAOImpl.getInstance().getById(companyId);
		}

		return new Computer(computerId, company, computerName, discontinuedDate, introducedDate);
	}

}
