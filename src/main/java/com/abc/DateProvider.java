package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Eddy Brown
 * 
 * DateProvider class used in transaction time stamps and other time related methods
 *
 */
class DateProvider {
	
    private static DateProvider instance = null;

    static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * 
     * @return The time now
     */
    Date now() {
        return Calendar.getInstance().getTime();
    }
    
    /**
     * Returns the date difference from a given date to today in days.
     * @param 		Date to check age
     * @return 		Age in days
     */
    long dateDifference(Date date) {
        long difference = now().getTime() - date.getTime();
        return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS); 
    }
}
