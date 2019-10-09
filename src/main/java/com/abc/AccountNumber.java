package com.abc;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Eddy Brown
 * 
 * Generates a unique random number between the minimum and maximum values defined below.
 *
 */
class AccountNumber {
	
    private static AccountNumber instance = null;
    
    private final int MAX_ACCOUNT_NUMBER = 999999;
    private final int MIN_ACCOUNT_NUMBER = 100000; 

    static AccountNumber getInstance() {
        if (instance == null)
            instance = new AccountNumber();
        return instance;
    }	
	int generateAccountNumber() {
		return ThreadLocalRandom.current().nextInt(MIN_ACCOUNT_NUMBER, MAX_ACCOUNT_NUMBER + 1);
	}
	
}
