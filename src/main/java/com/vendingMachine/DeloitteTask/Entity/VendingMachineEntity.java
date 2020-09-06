package com.vendingMachine.DeloitteTask.Entity;

/**
 * VendingMachineEntity is used to maintain the product and coin for the
 * particular transaction
 * 
 * @author Nanda Babu A
 *
 * @param <Product>
 * @param <Coin>
 */
public class VendingMachineEntity<Product, Coin> {

	/** product represents the user selected product */
	private Product product;

	/** coin represents the user inputed coins */
	private Coin coin;

	public VendingMachineEntity(Product product, Coin coin) {
		this.product = product;
		this.coin = coin;
	}

	/**
	 * getProduct returns the selected product
	 * 
	 * @return product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * getCoin returns the inputed coins
	 * 
	 * @return coin
	 */
	public Coin getCoin() {
		return coin;
	}
}
