package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eddy Brown
 * 
 * Bank class contains a reference to all customers and therefore all accounts and transactions.
 *
 */
public class Bank {
	
	/*
	 * A list for customers who use this bank
	 */
    private List<Customer> customers;

    /*
     * A bank consists of customers
     */
    Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Add a customer to the bank. This is used in the Customer constructor as a customer must have a bank
     * in order to have an account.
     * @param customer
     */
    void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Produces a summary of customers currently linked to the bank as well as how many accounts they have.
     * @return String
     */
    String customerSummary() {
    	if (customers.isEmpty())
    		return "You have no customers";
    	else {
	        String summary = "Customer Summary";
	        for (Customer c : customers)
	            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
	        return summary;
    	}
    }

    /**
     * Prints account or accounts depending on how many accounts there are
     * @param number	Of accounts
     * @param word		Word to pluralise (we are using account)
     * @return String	Pluralised word if number of accounts is greater than 1
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
    
    /**
     * Gets the amount of interest the account will earn in a year
     * @param account		Account you'd like to check
     * @return double		The amount of interest earned in a year
     */
    double getAccountInterest(Account account) {
    	return account.interestEarned();
    }
    
    /**
     * Total amount of interest paid to the bank from all accounts
     * @return double
     */
    double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
    
    /**
     * Allows the bank to find the account using the account number as a reference
     * @param accountNumber		
     * @return Account
     */
    Account getAccountByNumber(int accountNumber) {
    	
    	// Check all customers
    	for (Customer c : customers) {
    		// And all their accounts
    		for (Account a : c.getAccounts()) {
    			if (a.getAccountNumber() == accountNumber)
    				return a;
    		}
    	}
    	// Return null if no account is found
    	return null;
    }
}
