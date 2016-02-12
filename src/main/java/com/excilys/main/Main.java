package com.excilys.main;

import com.excilys.UI.CommandLineInterface;

public class Main {

	public static void main(String[] args) {
		
		/*Logger logger = LoggerFactory.getLogger("com.excilys.main.Main");
	    logger.debug("Hello world.");*/
		
		CommandLineInterface commandListenner = new CommandLineInterface();
		commandListenner.launch();
		
	}
}
