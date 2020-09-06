package com.vendingMachine.DeloitteTask.Exception;

/**
 * NotSufficientChangeException is used to throw exception when there is no
 * denomination to give to the user
 * 
 * @author Nanda Babu A
 *
 */
public class NotSufficientChangeException extends RuntimeException {

	private String message;

	public NotSufficientChangeException(String string) {
		this.message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
