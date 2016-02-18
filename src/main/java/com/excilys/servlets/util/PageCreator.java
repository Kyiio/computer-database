package com.excilys.servlets.util;

import java.util.ArrayList;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.PageDTO;
import com.excilys.model.QueryParameters;

public interface PageCreator {

	public static PageDTO buildPage(QueryParameters queryParameters, ArrayList<ComputerDTO> computerDTOList, int totalNumberOfComputer){
		
		int pageSize = queryParameters.getPageSize();
		int maxPageNumber = (int) Math.ceil((double) totalNumberOfComputer / (double) pageSize);
		
		PageDTO page = new PageDTO(pageSize, queryParameters.getPageNumber(), totalNumberOfComputer, maxPageNumber, computerDTOList);
		return page;		
	}
	
}
