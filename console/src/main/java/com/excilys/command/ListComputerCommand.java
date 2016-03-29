package com.excilys.command;

import com.excilys.dto.ComputerDto;
import com.excilys.dto.PageDto;

import java.util.Scanner;

import javax.ws.rs.client.WebTarget;

/**
 * Class that extends the AbstractCommand class and that is used in the CLI in order to retrieve and
 * print the Computer List.
 * 
 * @author B. Herbaut
 */
public class ListComputerCommand extends AbstractCommand {

  public ListComputerCommand(Scanner scanner) {
    super(scanner);
  }

  private String getClientInput() {

    String clientInput = "";

    boolean isInputOk = false;

    while (!isInputOk) {

      clientInput = scanner.nextLine().trim().toUpperCase();

      if ("QUIT".equals(clientInput) || "NEXT".equals(clientInput)
          || "PREVIOUS".equals(clientInput)) {
        isInputOk = true;
      } else {
        System.out.println(
            "You can navigate with 'NEXT' and 'PREVIOUS', type 'QUIT' to return to the menu");
      }
    }

    return clientInput;
  }

  @Override
  public void execute() {

    System.out.println(
        "Here is the list of all the company's computer page per page(navigate with 'next' "
            + "and 'previous', type 'Q' to return to the menu):");

    int pageNumber = 1;
    final int pageSize = 10;

    // We retrieve the first page

    WebTarget target =
        baseUrl.path("/computer/page").queryParam("pageNumber", pageNumber).queryParam("pageSize",
            pageSize);
    PageDto pageDto = target.request().get(PageDto.class);

    int maxPageNumber = pageDto.getMaxPageNumber();
    String clientInput = "";

    // We listen for user input in order to display the pages or to quit
    while (!"QUIT".equals(clientInput)) {

      // We print the current page
      for (ComputerDto computer : pageDto.getContent()) {
        System.out.println(computer);
      }
      System.out.println();

      // Then we await for user input

      System.out.print("CDB_Project, computerList >> ");

      clientInput = getClientInput();

      if ("NEXT".equals(clientInput)) {

        pageNumber = (pageNumber < maxPageNumber ? pageNumber + 1 : 1);

        pageDto = baseUrl
            .path("/computer/page")
            .queryParam("pageNumber", pageNumber)
            .queryParam("pageSize", pageSize)
            .request()
            .get(PageDto.class);

      } else if ("PREVIOUS".equals(clientInput)) {

        pageNumber = (pageNumber > 1 ? pageNumber - 1 : maxPageNumber);

        pageDto = baseUrl
            .path("/computer/page")
            .queryParam("pageNumber", pageNumber)
            .queryParam("pageSize", pageSize)
            .request()
            .get(PageDto.class);
      }
    }
  }
}
