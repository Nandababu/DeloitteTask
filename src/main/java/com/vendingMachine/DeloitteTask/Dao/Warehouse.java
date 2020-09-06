package com.vendingMachine.DeloitteTask.Dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Warehouse acts as a dao class for maintaining the count of products and coins
 * 
 * @author Nanda Babu A
 *
 * @param <Product/Coin>
 */
public class Warehouse<T> {

	private Map<T, Integer> warehouse = new HashMap<T, Integer>();

	/**
	 * getQuantity is used to return the quantity of products/coins from warehouse
	 * 
	 * @param product
	 * @return quantityValue
	 */
	public int getQuantity(T product) {
		Integer value = warehouse.get(product);
		return value == null ? 0 : value;
	}

	/**
	 * add is used to add the quantity of inputed coins in warehouse
	 * 
	 * @param product
	 */
	public void add(T product) {
		int count = warehouse.get(product);
		warehouse.put(product, count + 1);
	}

	/**
	 * deduct is used to reduce the values from the warehouse with the current
	 * products/coins
	 * 
	 * @param product
	 */
	public void deduct(T product) {
		if (hasProduct(product)) {
			int count = warehouse.get(product);
			warehouse.put(product, count - 1);
		}
	}

	/**
	 * hasProduct checks whether the products/coins are present in the warehouse or
	 * not
	 * 
	 * @param product
	 * @return booleanValue
	 */
	public boolean hasProduct(T product) {
		return getQuantity(product) > 0;
	}

	/**
	 * clear method is used to empty the products/coins warehouse
	 */
	public void clear() {
		warehouse.clear();
	}

	/**
	 * put method is used to set the initial values of products/coins to the
	 * warehouse in vendor machine
	 * 
	 * @param product
	 * @param quantity
	 */
	public void put(T product, int quantity) {
		warehouse.put(product, quantity);
	}

}