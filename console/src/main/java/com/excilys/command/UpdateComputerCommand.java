package com.excilys.command;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.ComputerDto;

import java.util.Scanner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Class that extends the AbstractCommand class and that is used in the CLI in order to handle the
 * update of a computer by the user in the database.
 * 
 * @author B. Herbaut
 */
public class UpdateComputerCommand extends AbstractCommand {

  public UpdateComputerCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    /* We ask for the name of the computer the use want to change */

    ComputerDto oldComputer = askForMethodToFindComputer("update");

    if (oldComputer == null) {
      return;
    }

    /* New name */

    System.out.println("Now enter the new informations:\n");
    String name = askForNewComputerName();

    if (name == null) {
      return;
    }

    oldComputer.setComputerName(name);

    /* New introduced date */

    oldComputer.setIntroducedDate(askForDate("Introduced"));

    /* New discontinued date */

    oldComputer.setDiscontinuedDate(askForDate("Discontinued"));

    /* New company associated to the computer */

    CompanyDto company = askForExistingCompanyByAskingName();

    if (company != null) {
      oldComputer.setCompanyName(company.getName());
      oldComputer.setCompanyId(company.getId());
    } else {
      oldComputer.setCompanyName(null);
      oldComputer.setCompanyId(-1);
    }

    WebTarget target = baseUrl.path("/computer/edit");

    String results =
        target.request().post(Entity.entity(oldComputer, MediaType.APPLICATION_JSON), String.class);

    System.out.println(results);
  }

}