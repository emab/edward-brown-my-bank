package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Eddy Brown
 * 
 * Generates a unique random number between the minimum and maximum values defined below.
 *
 */
class AccountNumber {
	
    private static AccountNumber instance = null;
    
    private List<Integer> accountNumbers = new ArrayList<Integer>();
    
    
    private final int MAX_ACCOUNT_NUMBER = 999999;
    private final int MIN_ACCOUNT_NUMBER = 100000; 
    
    private int accountIndex = 0;

    static AccountNumber getInstance() {
        if (instance == null)
            instance = new AccountNumber();
        return instance;
    }
    
    /**
     * Populates a list of possible account numbers
     */
    public AccountNumber() {
    	for (int i=MIN_ACCOUNT_NUMBER; i<=MAX_ACCOUNT_NUMBER; i++) {
    		accountNumbers.add(i);
    	}
        Collections.shuffle(accountNumbers);
    }
 
    /**
     * One unique account number is selected each time
     */
	int generateAccountNumber() {
		accountIndex++;
		return accountNumbers.get(accountIndex);
		
	}
	
}
