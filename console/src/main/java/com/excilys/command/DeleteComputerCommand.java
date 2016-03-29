package com.excilys.command;

import com.excilys.dto.ComputerDto;

import java.util.Scanner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Class that extends the AbstractCommand class and that is used in the CLI in order to handle the
 * deletion of a computer in the database by the user.
 * 
 * @author B. Herbaut
 */
public class DeleteComputerCommand extends AbstractCommand {

  public DeleteComputerCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    ComputerDto computer = askForMethodToFindComputer("delete");

    if (computer == null) {
      return;
    }

    WebTarget target = baseUrl.path("/computer/delete");

    String results = target.request().post(
        Entity.entity(computer.getComputerId(), MediaType.APPLICATION_JSON), String.class);

    System.out.println(results);
  }
}