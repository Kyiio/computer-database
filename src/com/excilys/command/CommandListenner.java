package com.excilys.command;

import java.util.Scanner;

public class CommandListenner {

	enum CommandType{
		INSERT_COMPUTER,
		DELETE_COMPUTER,
		UPDATE_COMPUTER,
		LIST_COMPUTER,
		LIST_COMPANIES,
		HELP,
		QUIT,
		UNKNOWN_COMMAND
	}
	
	public CommandListenner() {
				
	}
	
	public void showHelp(){
		
		System.out.println("\n####################Command List#####################\n");
		System.out.println("Type " + CommandType.INSERT_COMPUTER + " to insert computer");
		System.out.println("Type " + CommandType.DELETE_COMPUTER + " to delete computer");
		System.out.println("Type " + CommandType.UPDATE_COMPUTER + " to update computer");
		System.out.println("Type " + CommandType.LIST_COMPUTER + " to list computer");
		System.out.println("Type " + CommandType.LIST_COMPANIES + " to list companies\n");
		
		System.out.println("Type " + CommandType.HELP + " to review the command list !");
		System.out.println("Type " + CommandType.QUIT + " when you are done !");
		
	}
	
	public void launch(){
		
		System.out.println("#####################################################");
		System.out.println("############Computer Database Project CLI############");
		System.out.println("#####################################################\n");
		
		showHelp();
		
		Scanner scanner = new Scanner(System.in);
		
		boolean continueListenning = true;
		
		CommandInvoker commandInvoker = new CommandInvoker();
		
		while(continueListenning){
		
			System.out.print("CDB_Project >> ");
			
			CommandType line = CommandType.UNKNOWN_COMMAND;
			
			try {
				line = CommandType.valueOf(scanner.nextLine().trim());
			} catch (IllegalArgumentException e) {
				
			}
		
			switch (line) {
				case INSERT_COMPUTER:
					commandInvoker.setCommand(new InsertComputerCommand(scanner));
					break;
				
				case UPDATE_COMPUTER:
					commandInvoker.setCommand(new UpdateComputerCommand(scanner));
					break;
				
				case DELETE_COMPUTER:
					commandInvoker.setCommand(new DeleteComputerCommand(scanner));
					break;
					
				case LIST_COMPANIES:
					commandInvoker.setCommand(new ListCompaniesCommand(scanner));
					break;
				
				case LIST_COMPUTER:
					commandInvoker.setCommand(new ListComputerCommand(scanner));
					break;
				
				case HELP:
					this.showHelp();
					break;
					
				case QUIT:
					
					System.out.println("Bye !");
					continueListenning = false;
					break;
					
				default:
					
					System.out.println("Unknown command !");
					
					break;
			}
			
			commandInvoker.executeCommand();
			
		}
	}
	
}
