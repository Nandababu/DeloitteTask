package com.vendingMachine.DeloitteTask.service;

import java.util.List;
import java.util.Map;

import com.vendingMachine.DeloitteTask.Entity.VendingMachineEntity;
import com.vendingMachine.DeloitteTask.Enum.Coin;
import com.vendingMachine.DeloitteTask.Enum.Product;

/**
 * VendingMachine is used to perform operations on vendor machine
 * 
 * @author Nanda Babu A
 *
 */
public interface VendingMachine {

	long selectProductAndGetPrice(Product product);

	void insertCoin(Coin coin);

	List<Coin> refund();

	VendingMachineEntity<Product, List<Coin>> collectProductAndChange();

	void reset();

	Map<String, Integer> getProductInWarehouse();

	Map<String, Integer> getCashInWarehouse();

	long getTotalSales();

}
