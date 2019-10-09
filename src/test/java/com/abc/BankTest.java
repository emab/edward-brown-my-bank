package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Eddy Brown
 * 
 * Testing features related to the bank
 *
 */
public class BankTest {

	/*
	 * The manager should be able to get a summary if there are customers
	 */
	@Test
    public void bank_manager_can_get_customer_summary() {
		
		// Given a bank
        Bank bank = new Bank();
        
        // And a customer that belongs to the bank with an account
        Customer john = new Customer("John", bank);
        john.openAccount(Account.CHECKING);

        // The customer summary should display the information
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
	/*
	 * The manager should get a special message if no customers are registered
	 */
    @Test
    public void bank_manager_has_no_customers() {
    	
    	Bank bank = new Bank();
    	assertEquals("You have no customers", bank.customerSummary());
    }
    
    /*
     * If the bank has multiple customers and accounts they should display properly
     */
    @Test
    public void bank_manager_can_get_multiple_customer_summary() {
    	
    	// A bank with two customers
    	Bank bank = new Bank();
    	Customer john = new Customer("John", bank);
    	Customer bill = new Customer("Bill", bank);
    	
    	// And three accounts
    	john.openAccount(Account.CHECKING);
    	bill.openAccount(Account.CHECKING);
    	bill.openAccount(Account.SAVINGS);
    	
    	// Check it is properly displayed
    	assertEquals("Customer Summary\n - John (1 account)"
    			+ "\n - Bill (2 accounts)", bank.customerSummary());
    }

}
