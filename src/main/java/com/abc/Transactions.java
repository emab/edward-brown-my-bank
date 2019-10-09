package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eddy Brown
 * 
 * Transactions class is used to manage the transactions.
 * It performs some basic checks to make sure a transaction can take place before storing them.
 * It can also be used to get any transactions relating to an account.
 *
 */
class Transactions {
	
	/*
	 * List of the transactions
	 */
	private List<Transaction> transactionList;
	
	private static Transactions instance = null;
	
    static Transactions getInstance() {
        if (instance == null)
            instance = new Transactions();
        return instance;
    }
	
    
	private Transactions() {
		transactionList = new ArrayList<Transaction>();
	}
	
	/**
	 * Gets the transactions related to the account
	 * @param account
	 * @return List<Transaction>
	 */
	List<Transaction>getTransactions(Account account) {
		List<Transaction> accountTransactions = new ArrayList<Transaction>();
			for(Transaction trans : transactionList) {
				if (trans.getToAccount() == account || trans.getFromAccount() == account) {
					accountTransactions.add(trans);
				}
			}
		return accountTransactions;
	}
	
	/**
	 * Adds a transaction.
	 * @param amount		The transaction amount
	 * @param fromAccount	The account the amount is coming from
	 * @param toAccount		The account the amount is going to 
	 */
	void add(double amount, Account fromAccount, Account toAccount) {
		
		// We check that if the account is sending money to another account that they have the funds
		if (fromAccount != null && fromAccount.getBalance() - amount <= 0) {
			return;
		}
		
		// If they do have the funds
		else {
			try {
				transactionList.add(new Transaction(amount, fromAccount, toAccount));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    
	}
}
