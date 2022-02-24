package com.manjunatha.zooplus.orderdetails;

import java.math.BigDecimal;

public class TestNegativeValue {

	public static void main(String[] args) {

		/*
		 * BigDecimal totalAmountToCalcaulate = BigDecimal.ZERO;
		 * 
		 * String input1 = "100.00"; String input2 = "-345.45";
		 * 
		 * BigDecimal totalCalculateAmount = new BigDecimal(input1); BigDecimal
		 * customerBalanceAmount = new BigDecimal(input2);
		 * 
		 *Total calculated Amount never be negative
		 * 
		 * if (customerBalanceAmount.compareTo(BigDecimal.ZERO) == 0) { // Assuming
		 * Customer Balance is 0 totalAmountToCalcaulate =
		 * customerBalanceAmount.add(totalCalculateAmount); } else if
		 * (customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0 &&
		 * customerBalanceAmount.compareTo(totalCalculateAmount) > 0) { // Assuming
		 * customer Balance is Positive totalAmountToCalcaulate =
		 * customerBalanceAmount.subtract(totalCalculateAmount);
		 * System.out.println("Condition 1");
		 * 
		 * } else if (customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0 &&
		 * customerBalanceAmount.compareTo(totalCalculateAmount) < 0) { // Assuming
		 * customer Balance is Positive totalAmountToCalcaulate =
		 * customerBalanceAmount.subtract(totalCalculateAmount);
		 * System.out.println("Condition 2"); } else if
		 * (customerBalanceAmount.compareTo(BigDecimal.ZERO) < 0) { // Assuming Customer
		 * Balance is negative totalAmountToCalcaulate =
		 * totalCalculateAmount.add(customerBalanceAmount.negate());
		 * totalAmountToCalcaulate = totalAmountToCalcaulate.negate();
		 * System.out.println("Condition 3"); }
		 * 
		 * // Display the result in BigDecimal System.out.println("The sum of\n" +
		 * customerBalanceAmount + " \nand\n" + totalCalculateAmount + " " + "\nis\n" +
		 * totalAmountToCalcaulate + "\n");
		 * 
		 * }
		 */
	
	
	BigDecimal orderBalance = BigDecimal.ZERO;

	String input1 = "1000";
	String input2 = "-20000";

	BigDecimal paidAmount = new BigDecimal(input1);
	BigDecimal invoiceAmount = new BigDecimal(input2);
	//Considering always paid Amount is Positive

	if (invoiceAmount.compareTo(BigDecimal.ZERO) == 0) {
		// Assuming Customer Balance is 0
		orderBalance = paidAmount.add(invoiceAmount);
		} else if (invoiceAmount.compareTo(BigDecimal.ZERO) > 0 && invoiceAmount.compareTo(paidAmount) > 0) { // Assuming customer Balance isPositive 
			
			orderBalance = paidAmount.subtract(invoiceAmount);
			  System.out.println("Condition 1");
			  
		} else if (invoiceAmount.compareTo(BigDecimal.ZERO) > 0 && invoiceAmount.compareTo(paidAmount) < 0) {
			orderBalance = paidAmount.subtract(invoiceAmount);
			  System.out.println("Condition 2"); 
		} else if (invoiceAmount.compareTo(BigDecimal.ZERO) < 0) { 
			// AssumingCustomer Balance is negative 
			
			  orderBalance =paidAmount.subtract(invoiceAmount.negate()); 
			  //orderBalance =totalAmountToCalcaulate.negate(); 
			  System.out.println("Condition 3"); 
			  
		}
			 
			 
			 

	// Display the result in BigDecimal
	System.out.println("The sum of\n" + paidAmount + " \nand\n" + invoiceAmount + " " + "\nis\n"
			+ orderBalance + "\n");

}

}
