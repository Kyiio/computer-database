package com.excilys.command;

/**
 * The Class CommandInvoker.
 *
 * @author B. Herbaut
 */
public class CommandInvoker {

  /** The command. */
  AbstractCommand command;

  /**
   * Instantiates a new command invoker.
   */
  public CommandInvoker() {

  }

  /**
   * Method that execute the {@link AbstractCommand} object.
   */
  public void executeCommand() {

    if (command != null) {
      command.execute();
      command = null;
    }
  }

  /**
   * Sets the command.
   *
   * @param command
   *          the new command
   */
  public void setCommand(AbstractCommand command) {
    this.command = command;
  }
}
