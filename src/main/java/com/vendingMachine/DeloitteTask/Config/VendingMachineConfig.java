package com.vendingMachine.DeloitteTask.Config;

import com.vendingMachine.DeloitteTask.service.VendingMachine;
import com.vendingMachine.DeloitteTask.service.impl.VendingMachineImpl;

/**
 * VendingMachineConfig class is used to create object for the vending machine
 * with the initialization of properties
 * 
 * @author Nanda Babu A
 */
public class VendingMachineConfig {

	public static VendingMachine createVendingMachine(int numberOfCoins, int numberOfProducts) {
		return new VendingMachineImpl(numberOfCoins, numberOfProducts);
	}

}
