package com.excilys.main;

import com.excilys.command.CommandListenner;

public class Main {

	public static void main(String[] args) {
		
		CommandListenner commandListenner = new CommandListenner();
		commandListenner.launch();
		
	}
	
}
