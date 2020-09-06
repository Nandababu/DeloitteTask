package com.vendingMachine.DeloitteTask.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vendingMachine.DeloitteTask.Entity.VendingMachineEntity;
import com.vendingMachine.DeloitteTask.Dao.Warehouse;
import com.vendingMachine.DeloitteTask.Enum.Coin;
import com.vendingMachine.DeloitteTask.Enum.Product;
import com.vendingMachine.DeloitteTask.Exception.NotFullPaidException;
import com.vendingMachine.DeloitteTask.Exception.NotSufficientChangeException;
import com.vendingMachine.DeloitteTask.Exception.SoldOutException;
import com.vendingMachine.DeloitteTask.service.VendingMachine;

/**
 * VendingMachineImpl is the implementation class of vending machine
 * 
 * @author Nanda Babu A
 *
 */
public class VendingMachineImpl implements VendingMachine {

	private Warehouse<Coin> cashWarehouse = new Warehouse<Coin>();
	private Warehouse<Product> productWarehouse = new Warehouse<Product>();
	private long totalSales;
	private Product currentProduct;
	private long currentBalance;

	public VendingMachineImpl(int numberOfCoins, int numberOfProducts) {
		initialize(numberOfCoins, numberOfProducts);
	}

	private void initialize(int numberOfCoins, int numberOfProducts) {
		for (Coin c : Coin.values()) {
			cashWarehouse.put(c, numberOfCoins);
		}
		for (Product i : Product.values()) {
			productWarehouse.put(i, numberOfProducts);
		}
	}

	public long selectProductAndGetPrice(Product product) {
		if (productWarehouse.hasProduct(product)) {
			currentProduct = product;
			return currentProduct.getPrice();
		}
		throw new SoldOutException("Sold Out, Please buy another product");
	}

	public void insertCoin(Coin coin) {
		currentBalance = currentBalance + coin.getDenomination();
		cashWarehouse.add(coin);
	}

	public VendingMachineEntity<Product, List<Coin>> collectProductAndChange() {
		Product product = collectProduct();
		totalSales = totalSales + currentProduct.getPrice();

		List<Coin> change = collectChange();

		return new VendingMachineEntity<Product, List<Coin>>(product, change);
	}

	private Product collectProduct() throws NotSufficientChangeException, NotFullPaidException {
		if (isFullPaid()) {
			if (hasSufficientChange()) {
				productWarehouse.deduct(currentProduct);
				return currentProduct;
			}
			throw new NotSufficientChangeException("Not Sufficient change in Warehouse");

		}
		long remainingBalance = currentProduct.getPrice() - currentBalance;
		throw new NotFullPaidException("Price not fully paid, remaining : ", remainingBalance);
	}

	private List<Coin> collectChange() {
		long changeAmount = currentBalance - currentProduct.getPrice();
		List<Coin> change = getChange(changeAmount);
		updateCashWarehouse(change);
		currentBalance = 0;
		currentProduct = null;
		return change;
	}

	public List<Coin> refund() {
		List<Coin> refund = getChange(currentBalance);
		updateCashWarehouse(refund);
		currentBalance = 0;
		currentProduct = null;
		return refund;
	}

	private boolean isFullPaid() {
		if (currentBalance >= currentProduct.getPrice()) {
			return true;
		}
		return false;
	}

	private List<Coin> getChange(long amount) throws NotSufficientChangeException {
		List<Coin> changes = Collections.emptyList();
		if (amount > 0) {
			changes = new ArrayList<Coin>();
			long balance = amount;
			while (balance > 0) {
				if (balance >= Coin.QUARTER.getDenomination() && cashWarehouse.hasProduct(Coin.QUARTER)) {
					changes.add(Coin.QUARTER);
					balance = balance - Coin.QUARTER.getDenomination();
					continue;
				} else if (balance >= Coin.DIME.getDenomination() && cashWarehouse.hasProduct(Coin.DIME)) {
					changes.add(Coin.DIME);
					balance = balance - Coin.DIME.getDenomination();
					continue;
				} else if (balance >= Coin.NICKLE.getDenomination() && cashWarehouse.hasProduct(Coin.NICKLE)) {
					changes.add(Coin.NICKLE);
					balance = balance - Coin.NICKLE.getDenomination();
					continue;
				} else if (balance >= Coin.PENNY.getDenomination() && cashWarehouse.hasProduct(Coin.PENNY)) {
					changes.add(Coin.PENNY);
					balance = balance - Coin.PENNY.getDenomination();
					continue;
				} else {
					throw new NotSufficientChangeException("NotSufficientChange, Please try another product");
				}
			}
		}
		return changes;
	}

	public void reset() {
		cashWarehouse.clear();
		productWarehouse.clear();
		totalSales = 0;
		currentProduct = null;
		currentBalance = 0;
	}

	public Map<String, Integer> getProductInWarehouse() {
		Map<String, Integer> resultMap = new HashMap();
		resultMap.put(Product.CANDY.toString(), productWarehouse.getQuantity(Product.CANDY));
		resultMap.put(Product.CHOCOLATE.toString(), productWarehouse.getQuantity(Product.CHOCOLATE));
		resultMap.put(Product.COOLDRINK.toString(), productWarehouse.getQuantity(Product.COOLDRINK));
		return resultMap;
	}

	public Map<String, Integer> getCashInWarehouse() {
		Map<String, Integer> resultMap = new HashMap();
		resultMap.put(Coin.PENNY.toString(), cashWarehouse.getQuantity(Coin.PENNY));
		resultMap.put(Coin.NICKLE.toString(), cashWarehouse.getQuantity(Coin.NICKLE));
		resultMap.put(Coin.DIME.toString(), cashWarehouse.getQuantity(Coin.DIME));
		resultMap.put(Coin.QUARTER.toString(), cashWarehouse.getQuantity(Coin.QUARTER));
		return resultMap;
	}

	private boolean hasSufficientChange() {
		return hasSufficientChangeForAmount(currentBalance - currentProduct.getPrice());
	}

	private boolean hasSufficientChangeForAmount(long amount) {
		boolean hasChange = true;
		try {
			getChange(amount);
		} catch (NotSufficientChangeException nsce) {
			return hasChange = false;
		}

		return hasChange;
	}

	private void updateCashWarehouse(List<Coin> change) {
		for (Coin c : change) {
			cashWarehouse.deduct(c);
		}
	}

	public long getTotalSales() {
		return totalSales;
	}
}