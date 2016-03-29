package com.excilys.command;

import com.excilys.dto.CompanyDto;

import java.util.ArrayList;
import java.util.Scanner;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Class that extends the AbstractCommand class and that is used in the CLI in order to retrieve and
 * print the Company List.
 * 
 * @author B. Herbaut
 */
public class ListCompaniesCommand extends AbstractCommand {

  public ListCompaniesCommand(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void execute() {

    WebTarget target = baseUrl.path("/company/list");

    ArrayList<CompanyDto> companies =
        target.request().get(new GenericType<ArrayList<CompanyDto>>() {
        });

    System.out.println("Here is the list of all the company :");

    for (CompanyDto companyDto : companies) {
      System.out.println(companyDto);
    }
  }

}
