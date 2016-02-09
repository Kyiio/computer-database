package com.excilys.command;

public class CommandInvoker {

	AbstractCommand command;
	
	public CommandInvoker() {

	}
	
	public void executeCommand(){
		
		if(command != null){
			command.execute();
			command = null;
		}		
	}
	
	public void setCommand(AbstractCommand command){
		this.command = command;
	}	
}
