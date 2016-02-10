package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.dao.ComputerDAO;
import com.excilys.exception.DAOException;
import com.excilys.exception.ServiceException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * 
 * @author Herbaut Bastien
 */
public abstract class ComputerService {

	/**
	 * Method that check the information in the computer and then call the updateComputer method from the Computer DAO
	 * @param computer
	 * @see ComputerDAO
	 */
	public abstract void updateComputer(Computer computer);
	
	/**
	 * Method that check the name and dates given in parameters and then call the insertComputer method from the Computer DAO
	 * @param company
	 * @param introduced
	 * @param discontinued
	 * @param name
	 * @return The id of the company we just inserted
	 * @see ComputerDAO
	 */
	public abstract int insertComputer(Company company, LocalDateTime introduced, LocalDateTime discontinued, String name);
	
	/**
	 * Method that calls the deleteComputer method from the Computer DAO
	 * @param id The id of the computer we want to delete
	 * @see ComputerDAO
	 */
	public abstract void deleteComputer(int id);
	
	/**
	 * Method that calls the getById method from the Computer DAO
	 * @param id The id of the computer we want to retrieve
	 * @return The computer that matches the given id or null
	 * @see ComputerDAO
	 */
	public abstract Computer getById(int id);
	
	/**
	 * Method that calls the getByName method from the Computer DAO
	 * @param name The name of the computer(s) we want to retrieve
	 * @return The list of computers that matches the given name (or an empty list)
	 * @see ComputerDAO
	 */
	public abstract ArrayList<Computer> getByName(String name);
	
	/**
	 * Method that calls the listComputers from the Computer DAO
	 * @return The list of all the computers in the database 
	 * @see ComputerDAO
	 */
	public abstract ArrayList<Computer> listComputers();
	
	/**
	 * Method that checks if the data are consistent
	 * @param name The computer name we want to check
	 * @param introduced Introduced date for the computer (should be before the discontinued)
	 * @param discontinued Discontinued date for the computer (should be after the introduced)
	 * @throws DAOException
	 */
	public void checkData(String name, LocalDateTime introduced, LocalDateTime discontinued) throws ServiceException{
		
		if(name == null || name.length() == 0){
			throw new ServiceException("Computer's name must be set !");
		}
		
		if((introduced == null && discontinued != null) || (introduced != null && discontinued != null && introduced.isAfter(discontinued))){
			throw new ServiceException("Discontinued date should be set after Introduced date ! Discontinued :" + discontinued + " Introduced :" + introduced);
		}
		
	}
	
	/**
	 * Method that checks if the computer's data are correct
	 * @param computer The computer that contains the data we have to check
	 * @throws DAOException
	 */
	public void checkData(Computer computer) throws ServiceException{
		
		checkData(computer.getName(), computer.getIntroduced(), computer.getDiscontinued());
		
	}
}
