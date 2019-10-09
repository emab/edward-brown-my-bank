package com.abc;

import java.util.Date;

/**
 * @author Eddy Brown
 * 
 * The transaction class represents a single account transaction.
 * A transaction can be between two accounts or one account.
 * A transaction between two accounts represents sending money from one account to another.
 * A transaction between one account represents withdrawing or depositing money from an account
 *
 */
class Transaction {
	
	/*
	 * The transaction amount
	 */
	private final double amount;
	
	/*
	 * The origin and destination accounts
	 */
    private final Account toAccount;
    private final Account fromAccount;

	/*
	 * The date of the transaction
	 */
	private final Date transactionDate;
	
	/*
	 * Used to define when a transaction is recent enough to lower Maxi Saver interest (in days)
	 */
	private final int TRANSACTION_AGE_DAYS = 10;

	private DateProvider dateProvider = DateProvider.getInstance();
    
	/**
	 * The transaction constructor checks that the amount being sent is greater than zero.
	 * Since a transaction always represents funds from one place to another, original funds must
	 * always be positive.
	 * @param amount		Amount to be transferred
	 * @param fromAccount	From which account to take money
	 * @param toAccount		From which account to send money
	 * @throws Exception	An exception is thrown if the amount is less than zero
	 */
    Transaction(double amount, Account fromAccount, Account toAccount) throws Exception {
    	if (amount < 0) 
    		throw new Exception("Amount must be a positive number");
    	else
    		this.amount = amount;
    	this.fromAccount = fromAccount;
    	this.toAccount = toAccount;
    	this.transactionDate = dateProvider.now();
    	
    	if (fromAccount != null)
    		fromAccount.updateBalance(-amount);
    	
    	if (toAccount != null)
    		toAccount.updateBalance(amount);
    }
    
    /**
     * Checks to see if the transaction is recent. Can be defined above.
     * @return
     */
    boolean IsRecentTransaction() {
    	if (DateProvider.getInstance().dateDifference(transactionDate) <= TRANSACTION_AGE_DAYS)
    		return true;
    	else
    		return false;
    }
    
    /**
     * 
     * @return the origin account
     */
    Account getFromAccount() {
		return fromAccount;
	}

    /**
     * 
     * @return the destination account
     */
	Account getToAccount() {
		return toAccount;
	}

	/**
	 * 
	 * @return the transaction amount
	 */
	double getAmount() {
		return amount;
	}
    
}
