package com.excilys.command;

import com.excilys.dto.CompanyDto;

import java.util.Scanner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class DeleteCompanyCommand extends AbstractCommand {

  public DeleteCompanyCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    CompanyDto company = askForExistingCompanyByAskingName();

    if (company == null) {
      return;
    }

    WebTarget target = baseUrl.path("/company/delete");

    String results = target
        .request()
        .post(Entity.entity(company.getId(), MediaType.APPLICATION_JSON), String.class);

    System.out.println(results);
  }
}