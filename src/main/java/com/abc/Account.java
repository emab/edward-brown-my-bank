package com.abc;
import java.util.List;

/**
 * @author Eddy Brown
 * 
 * This class contains all the information regarding an account.
 * There are three types of accounts: Checking, Savings and Maxi Savings. Each has a different interest rate.
 * A six-digit account number is randomly generated for each account.
 * This was done so that an account can be referenced by its account number rather than referencing the object.
 * From this class you can also access all transactions relating to this account.
 *
 */
class Account {

	/*
	 * Storing integer values to reference account types
	 */
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
	private final int accountType;
	
    /*
     * The account balance - initialises as zero
     */
    private double balance = 0;

    /*
     * Which customer does this account belong to
     */
    private final Customer customer;
    
    /*
     * Reference to the transactions manager
     */
    private Transactions transactions = Transactions.getInstance();
    
    /*
     * Account number so a customer can deposit or withdraw with their account number rather than just referencing objects
     */
    private final int accountNumber;

    /**
     * Account Constructor
     * @param customer		The customer who created the account
     * @param accountType	The type of account, represented by an int
     */
    Account(Customer customer, int accountType) {
    	
    	// Account number is generated via AccountNumber class
    	accountNumber = AccountNumber.getInstance().generateAccountNumber();
    	this.customer = customer;
        this.accountType = accountType;
    } 
    
    /**
     * Used to create a transaction from this account to another account
     * @param amount		The amount you want to send
     * @param toAccount		The account that will receive the funds
     */
    void sendMoney(double amount, Account toAccount) {
    	transactions.add(amount, this, toAccount);
    }
    
    /**
     * Deposit money into this account
     * @param amount		Amount you'd like to deposit
     */
    void deposit(double amount) {
    	transactions.add(amount, null, this);
    }

    /**
     * Withdraw money from this account
     * @param amount		Amount you'd like to withdraw
     */
	void withdraw(double amount) {
		transactions.add(amount, this, null);
	}

	/**
	 * Calculates the total amount of interest you will earn in a year
	 * @return double
	 */
    double interestEarned() {
        double amount = balance;
        switch(accountType){
        	case CHECKING:
        		return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
            	if (hasWithdrawn())
            		return amount * 0.001;
            	else
            		return amount * 0.05;
            default:
                throw new IllegalArgumentException("Invalid account type");
        }
    }
    
    /**
     * Returns a list of all the transactions related to this account
     * @return List<Transaction> 
     */
    List<Transaction> getTransactions() {
    	return transactions.getTransactions(this);
    }
    
    /**
     * Checks to see if the account has withdrawn any funds recently
     * @return boolean	Returns true if funds have been withdrawn recently
     */
    private boolean hasWithdrawn() {
    	for (Transaction trans : getTransactions()) {
    		if (trans.getFromAccount() == this && trans.IsRecentTransaction())
    			return true;
    	}
    	return false;
    }
    
    /**
     * Update the balance of the account. This is called after a transaction has been saved to ensure balance is correct
     * @param amount	Positive to increase balance, negative to decrease balance
     */
    void updateBalance(double amount) {
		this.balance += amount;
	}

	/**
	 * @return the accountType
	 */
	int getAccountType() {
		return accountType;
	}

	/**
	 * @return the balance
	 */
	double getBalance() {
		return balance;
	}

	/**
	 * @return the customer
	 */
	Customer getCustomer() {
		return customer;
	}

	/**
	 * @return the accountNumber
	 */
	int getAccountNumber() {
		return accountNumber;
	}
    
    
}
