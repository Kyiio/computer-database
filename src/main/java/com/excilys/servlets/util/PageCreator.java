package com.excilys.servlets.util;

import java.util.ArrayList;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.PageDTO;
import com.excilys.model.QueryParameters;

/**
 * Interface that provides a method to create a page dto from a
 * {@link QueryParameters} object, a list of {@link ComputerDTO}, and the total
 * count of computer in the database
 * 
 * @author B. Herbaut
 */
public interface PageCreator {

	public static PageDTO buildPage(QueryParameters queryParameters, ArrayList<ComputerDTO> computerDTOList,
			int totalNumberOfComputer) {

		int pageSize = queryParameters.getPageSize();
		int maxPageNumber = (int) Math.ceil((double) totalNumberOfComputer / (double) pageSize);

		PageDTO page = new PageDTO(pageSize, queryParameters.getPageNumber(), totalNumberOfComputer, maxPageNumber,
				computerDTOList, queryParameters.getSearch(), queryParameters.getBy(), queryParameters.getOrder());
		return page;
	}

}
