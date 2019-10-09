package com.abc;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * @author Eddy Brown
 * 
 * Testing features related to transactions
 *
 */
public class TransactionTest {
	
	/*
	 * Transactions can be created
	 */
    @Test
    public void transaction_can_be_created() throws Exception {
        Transaction t = new Transaction(0, null, null);
        assertTrue(t instanceof Transaction);
    }
    
    /*
     * Transactions can only be a positive amount, check for the exception
     */
    @Test
    public void transaction_must_be_postive_number() throws Exception {
    	assertThrows(Exception.class, () -> {
    		new Transaction(-5, null, null);
    	});
    }
    
    /*
     * A transaction sent to ones own account doesn't duplicate money
     */
    @Test
    public void transaction_to_self_does_not_duplicate_money() {
    	
    	// Given a customer and an account
    	Customer bill = new Customer("Bill", new Bank());
    	Account account = bill.openAccount(Account.CHECKING);
    	
    	// With some money
    	account.deposit(500);
    	
    	// Sending money to the same account shouldn't change the balance
    	bill.sendMoney(100, account.getAccountNumber(), account.getAccountNumber());
    	assertEquals(500, account.getBalance());
    }
    
    /*
     * You cannot withdraw money if you don't have enough money in your account
     */
    @Test
    public void cannot_withdraw_money_you_do_not_have() {
    	
    	// Given a customer with an account and $50
    	Customer bill = new Customer("Bill", new Bank());
    	Account account = bill.openAccount(Account.CHECKING);
    	account.deposit(50);
    	
    	// Try to withdraw $100
        bill.withdraw(account.getAccountNumber(), 100);
    	
        // Make sure the balance hasn't changed
    	assertEquals(50, account.getBalance());
    }
    
    /*
     * You cannot send money to another account if you do not have the money in your account
     */
    @Test
    public void cannot_send_money_you_do_not_have() {
    	
    	// Given a customer with an account with funds
    	Customer bill = new Customer("Bill", new Bank());
    	Account billAccount = bill.openAccount(Account.CHECKING);
    	billAccount.deposit(50);
    	
    	// And another account
    	Customer john = new Customer("John", new Bank());
    	Account johnAccount = john.openAccount(Account.CHECKING);
    	
    	// Try to send $100 ($50 more than we have)
    	billAccount.sendMoney(100, johnAccount);
    	
    	// Bills account should keep the same balance
    	assertEquals(50, billAccount.getBalance());
    }
    
    /*
     * A customer can perform a transaction to another of their account
     */
    @Test
    public void customer_can_perform_transaction_to_another_account() {
    	
    	// Given a bank with a customer and two accounts
    	Bank bank = new Bank();
    	Customer bill = new Customer("Bill", bank);
    	Account account1 = bill.openAccount(Account.CHECKING);
    	Account account2 = bill.openAccount(Account.CHECKING);
   
    	// If 500 is deposited
    	bill.deposit(account1.getAccountNumber(), 500);
    	// And 200 is sent to the other account
    	bill.sendMoney(200,account1.getAccountNumber(), account2.getAccountNumber());
    	
    	// The first account should have 300 remaining, the second account should receive the 200
    	assertEquals(300, account1.getBalance());
    	assertEquals(200, account2.getBalance());
    }
    
    /*
     * A customer can perform a transaction to another customers account
     */
    public void customer_can_perform_transaction_to_another_customer_account() {
    	
    	// Given a bank with two customers and two accounts
    	Bank bank = new Bank();
    	Customer bill = new Customer("Bill", bank);
    	Customer john = new Customer("John", bank);
    	Account billAccount = bill.openAccount(Account.CHECKING);
    	Account johnAccount = john.openAccount(Account.CHECKING);
    	
    	// If one account has some funds
    	billAccount.deposit(1000);
    	
    	// And sends 300 to johns empty account
    	bill.sendMoney(300, billAccount.getAccountNumber(), johnAccount.getAccountNumber());
    	
    	// We should see the corresponding changes
    	assertEquals(700, billAccount.getBalance());
    	assertEquals(300, johnAccount.getBalance());
    }
    
    /*
     * The sum of all transactions should give the account balance
     */
    @Test
    public void sum_of_transactions_for_account_equals_balance() {
    	
    	// Given a customer and two accounts
    	Customer bill = new Customer("Bill", new Bank());
    	Account account1 = bill.openAccount(Account.CHECKING);
    	Account account2 = bill.openAccount(Account.CHECKING);
    	
    	// And some transactions
    	account1.deposit(50);
    	account1.deposit(100);
    	account1.withdraw(10);
    	account1.sendMoney(10, account2);
    	
    	// The sum of transactions related to the account should be equal to balance
    	assertEquals(130, account1.getBalance());
    }
}
