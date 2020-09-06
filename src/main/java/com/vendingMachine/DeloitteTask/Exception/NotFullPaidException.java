package com.vendingMachine.DeloitteTask.Exception;

/**
 * NotFullPaidException is used to throw exception when the amount is not fully
 * paid
 * 
 * @author Nanda Babu A
 *
 */
public class NotFullPaidException extends RuntimeException {

	private String message;

	private long remaining;

	public NotFullPaidException(String message, long remaining) {
		this.message = message;
		this.remaining = remaining;
	}

	public long getRemaining() {
		return remaining;
	}

	@Override
	public String getMessage() {
		return message + remaining;
	}

}
