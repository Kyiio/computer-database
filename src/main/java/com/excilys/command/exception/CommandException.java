package com.excilys.command.exception;

/**
 * Exception that can be thrown when the user entered a wrong parameter in the CLI
 * 
 * @author B. Herbaut 
 *
 */
public class CommandException extends RuntimeException {

	private static final long serialVersionUID = -2611590685369154141L;

	public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

}