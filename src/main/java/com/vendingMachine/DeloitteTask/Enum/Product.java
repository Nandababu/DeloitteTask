package com.vendingMachine.DeloitteTask.Enum;

/**
 * Product enum class is used to denote the products inside the Vending Machine
 * 
 * @author Nanda Babu A
 *
 */
public enum Product {

	CHOCOLATE("Chocolate", 25),

	CANDY("Candy", 35),

	COOLDRINK("Cooldrink", 45);

	private String name;

	private int price;

	private Product(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public long getPrice() {
		return price;
	}

}
