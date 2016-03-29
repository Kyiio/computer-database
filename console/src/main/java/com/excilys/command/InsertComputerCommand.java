package com.excilys.command;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;

import java.util.Scanner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Class that extends the AbstractCommand class and that is used in the CLI in order to handle the
 * insertion of a computer in the database by the user.
 * 
 * @author B. Herbaut
 */
public class InsertComputerCommand extends AbstractCommand {

  public InsertComputerCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    ComputerDto newComputer = new ComputerDto();

    /* New name */
    System.out.println("Please enter information about the computer");
    String name = askForNewComputerName();

    if (name == null) {
      return;
    }

    newComputer.setComputerName(name);

    /* New introduced date */

    newComputer.setIntroducedDate(askForDate("Introduced"));

    /* New discontinued date */

    newComputer.setDiscontinuedDate(askForDate("Discontinued"));

    /* New company associated to the computer */

    CompanyDto company = askForExistingCompanyByAskingName();

    if (company != null) {
      newComputer.setCompanyName(company.getName());
      newComputer.setCompanyId(company.getId());
    }

    WebTarget target = baseUrl.path("/computer/add");

    String results =
        target.request().post(Entity.entity(newComputer, MediaType.APPLICATION_JSON), String.class);

    System.out.println(results);
  }
}
