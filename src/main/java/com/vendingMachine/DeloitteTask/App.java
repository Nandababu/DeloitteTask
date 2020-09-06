package com.vendingMachine.DeloitteTask;

import com.vendingMachine.DeloitteTask.Config.VendingMachineConfig;
import com.vendingMachine.DeloitteTask.service.VendingMachine;

/**
 * App is a main class where the vending machine can be processed.
 * 
 * @author Nanda Babu A
 *
 */
public class App {

	public static void main(String[] args) {
		VendingMachine vm = VendingMachineConfig.createVendingMachine(5, 5);
	}

}
