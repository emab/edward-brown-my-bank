package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Eddy Brown
 * 
 * Testing features related to the customer and their operations
 *
 */
public class CustomerTest {
	
    private static final double DOUBLE_DELTA = 1e-15;

    /*
     * A customer should be able to open an account
     */
    @Test
    public void customer_can_open_one_account() {
    	
    	// Given a customer
        Customer oscar = new Customer("Oscar", new Bank());
        
        // They are able to open an account
        oscar.openAccount(Account.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    /*
     * A customer should be able to open multiple accounts
     */
    @Test
    public void customer_can_open_two_accounts() {
    	
    	// Given a customer
        Customer oscar = new Customer("Oscar", new Bank());
        
        // Check they have two accounts
        oscar.openAccount(Account.SAVINGS);
        oscar.openAccount(Account.CHECKING);
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    /*
     * Multiple customers should be able to open multiple accounts
     */
    @Test
    public void multiple_customers_can_open_multiple_accounts() {
    	
    	// Given a bank
    	Bank bank = new Bank();
    	
    	// And a customer with two accounts
    	Customer oscar = new Customer("Oscar", bank);
    	oscar.openAccount(Account.SAVINGS);
    	oscar.openAccount(Account.SAVINGS);
    	
    	// And another with two accounts
    	Customer bill = new Customer("Bill", bank);
    	bill.openAccount(Account.SAVINGS);
    	bill.openAccount(Account.CHECKING);
    	
    	assertEquals(2, oscar.getNumberOfAccounts());
    	assertEquals(2, bill.getNumberOfAccounts());
    }
    
    /*
     * A customer should be able to deposit money into their account
     */
    @Test
    public void customer_can_deposit_into_account() {
    	
    	// Given a customer and an account
    	Customer bill = new Customer("Bill", new Bank());
    	Account account = bill.openAccount(Account.CHECKING);
    	
    	// If the customer deposits, it should show up
    	bill.deposit(account.getAccountNumber(), 100);
    	
    	assertEquals(100.0, account.getBalance(), DOUBLE_DELTA);
    }
    
    /*
     * A customer should be able to withdraw money from their account
     */
    @Test
    public void customer_can_withdraw_from_account() {
    	
    	// Given a customer and an account
    	Customer bill = new Customer("Bill", new Bank());
    	Account account = bill.openAccount(Account.CHECKING);
    	
    	// With some money in
    	bill.deposit(account.getAccountNumber(), 100);
    	
    	// Withdraw some money
    	bill.withdraw(account.getAccountNumber(), 50);
    	
    	assertEquals(50.0, account.getBalance(), DOUBLE_DELTA);
    }
    
    /*
     * Testing the customer statement operation with deposit and withdrawal transactions
     */
    @Test
    public void test_customer_statement_generation_with_deposit_and_withdrawal() {

    	// Given a customer with two accounts
        Customer henry = new Customer("Henry", new Bank());
        Account checkingAccount = henry.openAccount(Account.CHECKING);
        Account savingsAccount = henry.openAccount(Account.SAVINGS);

        // And some transactions
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        // Check the statement
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit: $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit: $4,000.00\n" +
                "  withdrawal: $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }
    
    /*
     * Testing the customer statement operation with transactions from another customer
     */
    @Test
    public void test_customer_statement_generation_with_transaction_from_another_customer() {
    	
    	// A bank
    	Bank bank = new Bank();
    	
    	// With two customers, one account each
    	Customer henry = new Customer("Henry", bank);
    	Account henryAccount = henry.openAccount(Account.CHECKING);
    	
    	Customer bill = new Customer("Bill", bank);
    	Account billAccount = bill.openAccount(Account.CHECKING);
    	billAccount.deposit(100.0);
    	
    	// And some transactions
    	bill.sendMoney(50, billAccount.getAccountNumber(), henryAccount.getAccountNumber());
    	henry.sendMoney(10, henryAccount.getAccountNumber(), billAccount.getAccountNumber());
    	
    	// Verify the output
    	assertEquals("Statement for Henry\n" +
    			"\n" +
    			"Checking Account\n" +
    			"  received $50.00 from Bill (" + billAccount.getAccountNumber() + ")\n" +
    			"  sent $10.00 to Bill (" + billAccount.getAccountNumber() + ")\n" +
    			"Total $40.00\n" +
    			"\n" +
    			"Total In All Accounts $40.00", henry.getStatement());
    }
}
