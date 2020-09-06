package com.vendingMachine.DeloitteTask.Exception;

/**
 * SoldOutException is used to throw exception when there is no product in terms
 * of quantity
 * 
 * @author Nanda Babu A
 *
 */
public class SoldOutException extends RuntimeException {

	private String message;

	public SoldOutException(String string) {
		this.message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
