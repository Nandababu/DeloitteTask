package com.vendingMachine.DeloitteTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vendingMachine.DeloitteTask.Config.VendingMachineConfig;
import com.vendingMachine.DeloitteTask.Entity.VendingMachineEntity;
import com.vendingMachine.DeloitteTask.Enum.Coin;
import com.vendingMachine.DeloitteTask.Enum.Product;
import com.vendingMachine.DeloitteTask.Exception.NotFullPaidException;
import com.vendingMachine.DeloitteTask.Exception.NotSufficientChangeException;
import com.vendingMachine.DeloitteTask.Exception.SoldOutException;
import com.vendingMachine.DeloitteTask.service.VendingMachine;

/**
 * VendingMachineTest is used to perform test on vending machine
 * 
 * @author NB
 *
 */
public class VendingMachineTest {

	private static VendingMachine vm;

	@Before
	public void setUp() {
		vm = VendingMachineConfig.createVendingMachine(5, 5);
	}

	@After
	public void tearDown() {
		vm = null;
	}

	@Test
	public void testInitialProductData() {
		Map<String, Integer> initialProductMap = new HashMap<String, Integer>();
		int initialData = 5;
		initialProductMap.put(Product.CANDY.toString(), initialData);
		initialProductMap.put(Product.CHOCOLATE.toString(), initialData);
		initialProductMap.put(Product.COOLDRINK.toString(), initialData);
		Assert.assertEquals(vm.getProductInWarehouse().toString(), initialProductMap.toString());
	}

	@Test
	public void testInitialCoinData() {
		Map<String, Integer> initialCoinMap = new HashMap<String, Integer>();
		int initialData = 5;
		initialCoinMap.put(Coin.PENNY.toString(), initialData);
		initialCoinMap.put(Coin.NICKLE.toString(), initialData);
		initialCoinMap.put(Coin.DIME.toString(), initialData);
		initialCoinMap.put(Coin.QUARTER.toString(), initialData);
		Assert.assertEquals(vm.getCashInWarehouse().toString(), initialCoinMap.toString());
	}

	@Test
	public void testPriceOfProducts() {
		Assert.assertEquals(25, Product.CHOCOLATE.getPrice());
		Assert.assertEquals(35, Product.CANDY.getPrice());
		Assert.assertEquals(45, Product.COOLDRINK.getPrice());
	}

	@Test
	public void testValueOfCoins() {
		Assert.assertEquals(1, Coin.PENNY.getDenomination());
		Assert.assertEquals(5, Coin.NICKLE.getDenomination());
		Assert.assertEquals(10, Coin.DIME.getDenomination());
		Assert.assertEquals(25, Coin.QUARTER.getDenomination());
	}

	@Test
	public void testBuyWithExactPrice() {

		vm.selectProductAndGetPrice(Product.CHOCOLATE);
		vm.insertCoin(Coin.QUARTER);

		VendingMachineEntity<Product, List<Coin>> entity = vm.collectProductAndChange();

		Product product = entity.getProduct();
		assertEquals(Product.CHOCOLATE, product);

		List<Coin> change = entity.getCoin();
		assertTrue(change.isEmpty());

		assertEquals(25, vm.getTotalSales());

		assertEquals("{NICKLE=5, PENNY=5, QUARTER=6, DIME=5}", vm.getCashInWarehouse().toString());

		assertEquals("{CHOCOLATE=4, COOLDRINK=5, CANDY=5}", vm.getProductInWarehouse().toString());

	}

	@Test(expected = NotFullPaidException.class)
	public void testBuyWithInsufficientPrice() {
		vm.selectProductAndGetPrice(Product.CANDY);
		vm.insertCoin(Coin.PENNY);

		vm.collectProductAndChange();
	}

	@Test
	public void testBuyWithExtraPrice() {
		vm.selectProductAndGetPrice(Product.COOLDRINK);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.QUARTER);

		VendingMachineEntity<Product, List<Coin>> entity = vm.collectProductAndChange();

		Product product = entity.getProduct();
		assertEquals(Product.COOLDRINK, product);

		List<Coin> change = entity.getCoin();
		assertFalse(change.isEmpty());

		assertEquals(45, vm.getTotalSales());

		assertEquals("{NICKLE=4, PENNY=5, QUARTER=7, DIME=5}", vm.getCashInWarehouse().toString());

		assertEquals("{CHOCOLATE=5, COOLDRINK=4, CANDY=5}", vm.getProductInWarehouse().toString());
	}

	@Test
	public void testRemainingWarehouseData() {
		vm.selectProductAndGetPrice(Product.CHOCOLATE);
		vm.insertCoin(Coin.QUARTER);
		vm.collectProductAndChange();

		vm.selectProductAndGetPrice(Product.CHOCOLATE);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.QUARTER);
		vm.collectProductAndChange();

		vm.selectProductAndGetPrice(Product.CANDY);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.DIME);
		vm.collectProductAndChange();

		vm.selectProductAndGetPrice(Product.CANDY);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.DIME);
		vm.insertCoin(Coin.PENNY);
		vm.collectProductAndChange();

		vm.selectProductAndGetPrice(Product.CANDY);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.DIME);
		vm.insertCoin(Coin.PENNY);
		vm.insertCoin(Coin.NICKLE);
		vm.collectProductAndChange();

		vm.selectProductAndGetPrice(Product.COOLDRINK);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.QUARTER);
		vm.collectProductAndChange();

		vm.selectProductAndGetPrice(Product.COOLDRINK);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.QUARTER);
		vm.collectProductAndChange();

		vm.selectProductAndGetPrice(Product.COOLDRINK);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.QUARTER);
		vm.collectProductAndChange();

		vm.selectProductAndGetPrice(Product.COOLDRINK);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.QUARTER);
		vm.collectProductAndChange();

		assertEquals(335, vm.getTotalSales());

		assertEquals("{NICKLE=1, PENNY=5, QUARTER=18, DIME=8}", vm.getCashInWarehouse().toString());

		assertEquals("{CHOCOLATE=3, COOLDRINK=1, CANDY=2}", vm.getProductInWarehouse().toString());
	}

	@Test(expected = SoldOutException.class)
	public void testSoldOut() {
		// when trying to get the sixth product error will be thrown
		for (int i = 0; i < 6; i++) {
			vm.selectProductAndGetPrice(Product.CHOCOLATE);
			vm.insertCoin(Coin.QUARTER);
			vm.collectProductAndChange();
		}
	}

	@Test(expected = SoldOutException.class)
	public void testReset() {
		vm.reset();
		vm.selectProductAndGetPrice(Product.CHOCOLATE);
	}

	@Test
	public void testRefund() {
		long price = vm.selectProductAndGetPrice(Product.CANDY);
		assertEquals(Product.CANDY.getPrice(), price);
		vm.insertCoin(Coin.PENNY);
		vm.insertCoin(Coin.NICKLE);
		vm.insertCoin(Coin.DIME);
		vm.insertCoin(Coin.QUARTER);

		VendingMachineEntity<Product, List<Coin>> entity = vm.collectProductAndChange();

		List<Coin> change = entity.getCoin();
		assertFalse(change.isEmpty());

		assertEquals(6, getTotal(change));
	}

	@Test(expected = NotSufficientChangeException.class)
	public void testNotSufficientChangeException() {
		vm = VendingMachineConfig.createVendingMachine(0, 5);
		vm.selectProductAndGetPrice(Product.COOLDRINK);
		vm.insertCoin(Coin.QUARTER);
		vm.insertCoin(Coin.QUARTER);
		vm.collectProductAndChange();
	}

	private long getTotal(List<Coin> change) {
		long total = 0;
		for (Coin c : change) {
			total = total + c.getDenomination();
		}
		return total;
	}

}