package com.vendingMachine.DeloitteTask.Enum;

/**
 * Coin enum class is used to denote the coins that can be accepted by Vending
 * Machine
 * 
 * @author Nanda Babu A
 *
 */
public enum Coin {

	PENNY(1),

	NICKLE(5),

	DIME(10),

	QUARTER(25);

	private int denomination;

	private Coin(int denomination) {
		this.denomination = denomination;
	}

	public int getDenomination() {
		return denomination;
	}

}
