package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author Eddy Brown
 * 
 * Testing features related to accounts
 *
 */
public class AccountTest {
	
    private static final double DOUBLE_DELTA = 1e-15;

    /*
     * A checking account should show the correct rates of interest over a year
     */
    @Test
    public void checking_account_has_correct_interest_rate() {
    	
    	// Given a bank with a customer with a checking account
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", bank);
        Account a = bill.openAccount(Account.CHECKING);
        
        // In a Checking account, $500 should get an interest of %0.1 so expect 0.5
        a.deposit(500);
        assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
     * A savings account should show the correct rates of interest over a year
     */
    @Test
    public void savings_account_has_correct_interest_rates() {
    	
    	// Given a bank with a customer with a savings account
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", bank);
        Account a = bill.openAccount(Account.SAVINGS);

        // In a Savings account, %0.1 for first $1000 so at $500 expect 0.5
        a.deposit(500.0);
        assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        // After first $1000 interest should be %0.2 so at $1500 expect 2
        a.deposit(1000.0);
        assertEquals(2, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
     * A maxi account should show the correct rates of interest over a year
     */
    @Test
    public void maxi_account_has_correct_interest_rates() {
    	
    	// Given a bank with a customer and two accounts
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", bank);
        
        //One with no withdrawals in the last 10 days, one with a recent transaction
        Account accountWithNoWithdrawals = bill.openAccount(Account.MAXI_SAVINGS);
        Account accountWithWithdrawals = bill.openAccount(Account.MAXI_SAVINGS);

        // In a Maxi account, %5 if no recent withdrawals so expect 50
        accountWithNoWithdrawals.deposit(1000.0);
        assertEquals(50, bank.getAccountInterest(accountWithNoWithdrawals), DOUBLE_DELTA);
        
        
        // %0.1 if a recent withdrawal has been made so expect 
        accountWithWithdrawals.deposit(1100.0);
        accountWithWithdrawals.withdraw(100.0);
        assertEquals(1, bank.getAccountInterest(accountWithWithdrawals), DOUBLE_DELTA);
    }

}
