package dev.indranil.idb.utils;

import java.time.Year;

public class AccountUtils {
	
	public static final String ACCOUNT_EXISTS_CODE = "001";
	public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an Account";
	public static final String ACCOUNT_CREATION_CODE = "002";
	public static final String ACCOUNT_CREATION_MESSAGE = "Account created successfully";
	public static final String ACCOUNT_NOT_EXISTS_CODE = "003";
	public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "User with the provided Account Number does not exists";
	public static final String ACCOUNT_FOUND_CODE = "004";
	public static final String ACCOUNT_FOUND_MESSAGE = "User Account Found";
	public static final String ACCOUNT_CREDITED_SUCCESS_CODE = "005";
	public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Account credited successfully";
	public static final String INSUFFICIENT_BALANCE_CODE = "006";
	public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficent Balance";
	public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "007";
	public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "Account Debited successfully";
	
	public static String generateAccountNumber() {
		/**
		 * 2024 + random 6 digits
		 */
		Year currentYear = Year.now();
		
		int min = 100000;
		int max = 999999;
		
		//	Random Number
		int randNumber = (int) Math.floor(Math.random() * ((max - min + 1) + min));
		
		// convert the current year and random number to string and then concatenate
		
		String year = String.valueOf(currentYear);
		String randomNumber = String.valueOf(randNumber);
		
		StringBuilder accountNumber = new StringBuilder();
		return accountNumber.append(year).append(randomNumber).toString();
	}
	
}
