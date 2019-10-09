package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * @author Eddy Brown
 * 
 * Customer class to represent a customer of the bank.
 * A customer must belong to a bank in order to be created.
 *
 */
class Customer {
	
	/*
	 * Bank of the customer
	 */
	private final Bank bank;
	
	/*
	 * Customers name and accounts
	 */
    private String name;
    private List<Account> accounts;
    

    /**
     * Customer constructor.
     * @param name		The customer name
     * @param bank		The customers bank
     */
    Customer(String name, Bank bank) {
    	this.bank = bank;
        this.name = name;
        this.accounts = new ArrayList<Account>();
        
        // Adds the customer to the banks customer list
        bank.addCustomer(this);
    }
    
    /**
     * Deposit money into one of the customers' accounts
     * @param myAccountNumber	An account number belonging to the customer
     * @param amount			Amount they'd like to deposit
     */
    void deposit(int myAccountNumber, double amount) {
    	findMyAccount(myAccountNumber).deposit(amount);
    }
    
    /**
     * Withdraw money from one of the customers' accounts
     * @param myAccountNumber	An account number belonging to the customer
     * @param amount			Amount they'd like to withdraw
     */
    void withdraw(int myAccountNumber, double amount) {
    	findMyAccount(myAccountNumber).withdraw(amount);
    }
    
    /**
     * Send money to another account from one of the customers' accounts
     * @param amount			Amount to send
     * @param myAccountNumber	Account belonging to customer from which to send
     * @param theirAccountNumber	The account number belonging to any customer or another account
     */
    void sendMoney(int amount, int myAccountNumber, int theirAccountNumber) {
    	findMyAccount(myAccountNumber).sendMoney(amount, bank.getAccountByNumber(theirAccountNumber));
    }

    /**
     * Open a new account for this customer
     * @param accountType
     * @return Account		Return the account as an object
     */
    Account openAccount(int accountType) {
    	Account account = new Account(this, accountType);
        accounts.add(account);
        return account;
    }

    /**
     * 
     * @return int	Number of accounts belonging to the customer
     */
    int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * 
     * @return double	Amount of interest that will be earned in a year
     */
    double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * Shows a statement displaying any transactions related to all of the customers accounts
     * @return String
     */
    String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /**
     * Shows the statement for a given account
     * @param a			The account
     * @return String	The details of transactions
     */
    private String statementForAccount(Account a) {
        String s = "";

       // Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }
        
        // Iterate through all transactions related to the account
        for (Transaction t : a.getTransactions()) {
        	// If the transaction has money sent too the account from a null account (deposit)
        	if (t.getToAccount() == a && t.getFromAccount() == null) {
        		s += "  " + ("deposit: ") + toDollars(t.getAmount()) + "\n";
        	}
        	
        	// If the transaction has money sent from the account to a null account (withdraw)
        	if (t.getFromAccount() == a && t.getToAccount() == null) {
        		s += "  " + ("withdrawal: " + toDollars(t.getAmount()) + "\n");
        	}
        	
        	// If the transaction has money sent to the account and is not from a null account (receive)
        	if (t.getToAccount() == a && t.getFromAccount() != null) {
        		s += "  " + ("received " + toDollars(t.getAmount()) + " from " +
        				t.getFromAccount().getCustomer().getName() + " (" + t.getFromAccount().getAccountNumber() + ")\n");
        	}
        	
        	// If the transaction has money sent from the account to a null account (sent)
        	if (t.getFromAccount() == a && t.getToAccount() != null) {
        		s += "  " + ("sent " + toDollars(t.getAmount()) + " to " +
        				t.getToAccount().getCustomer().getName() + " (" + t.getToAccount().getAccountNumber() + ")\n");
        	}
        }
        s += "Total " + toDollars(a.getBalance());
        return s;
    }
    
    /**
     * Finds an account owned by the customer using the account number as a reference
     * @param myAccountNumber
     * @return
     */
    private Account findMyAccount(int myAccountNumber) {
    	for (Account a : accounts) {
    		if (a.getAccountNumber() == myAccountNumber)
    			return a;
    	}
		return null;
    }
    
    /**
     * @return name
     */
    String getName() {
        return name;
    }
    
    /**
     * 
     * @return accounts
     */
    List<Account> getAccounts() {
    	return accounts;
    }

    /**
     * Formats the double to have 2 decimal points and a dollar sign
     * @param d				Amount in dollars
     * @return String		Formatted string
     */
    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
