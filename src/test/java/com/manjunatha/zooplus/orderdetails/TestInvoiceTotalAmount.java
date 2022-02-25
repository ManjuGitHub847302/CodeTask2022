package com.manjunatha.zooplus.orderdetails;

import java.math.BigDecimal;

public class TestInvoiceTotalAmount {

	public static void main(String[] args) {

		  
		  BigDecimal totalAmountToCalcaulate = BigDecimal.ZERO;
		  String productpriceAmount = "100"; 
		  String customerBalance = "1000";
		  
		  BigDecimal totalCalculateAmount = new BigDecimal(productpriceAmount); 
		  BigDecimal customerBalanceAmount = new BigDecimal(customerBalance);
		 //Total productPrice Amount never be negative
		  
		  if (customerBalanceAmount.compareTo(BigDecimal.ZERO) == 0 && totalCalculateAmount.compareTo(BigDecimal.ZERO) > 0) { 
			  // Assuming Customer Balance is 0 
			  totalAmountToCalcaulate =customerBalanceAmount.add(totalCalculateAmount); 
			  totalAmountToCalcaulate = totalAmountToCalcaulate.negate();
			  System.out.println("Condition Sp 1");
		  } else if (customerBalanceAmount.compareTo(BigDecimal.ZERO) == 0 && totalCalculateAmount.compareTo(BigDecimal.ZERO) < 0) { 
			  totalAmountToCalcaulate =customerBalanceAmount.add(totalCalculateAmount.negate()); 
			  totalAmountToCalcaulate = totalAmountToCalcaulate.negate();
			  System.out.println("Condition Sp 3");
		  } else if (totalCalculateAmount.compareTo(BigDecimal.ZERO) == 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0) { 
			  totalAmountToCalcaulate =customerBalanceAmount.add(totalCalculateAmount); 
			  System.out.println("Condition Sp 2");
		  } else if (totalCalculateAmount.compareTo(BigDecimal.ZERO) < 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) < 0) {
			  System.out.println("Condition 1");
			  totalAmountToCalcaulate = totalCalculateAmount.negate().add(customerBalanceAmount.negate());
			  totalAmountToCalcaulate = totalAmountToCalcaulate.negate();
		  } else if(totalCalculateAmount.compareTo(BigDecimal.ZERO) < 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0) {
			  System.out.println("Condition 2");
			  totalAmountToCalcaulate = totalCalculateAmount.negate().subtract(customerBalanceAmount);
			  totalAmountToCalcaulate = totalAmountToCalcaulate.negate();
		  } else if(totalCalculateAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) < 0) {
			  System.out.println("Condition 3");
			  totalAmountToCalcaulate = totalCalculateAmount.add(customerBalanceAmount.negate());
			  totalAmountToCalcaulate =totalAmountToCalcaulate.negate();
		  } else if(totalCalculateAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(totalCalculateAmount) > 0) {
			  System.out.println("Condition 4");
			  totalAmountToCalcaulate = customerBalanceAmount.subtract(totalCalculateAmount);
		  } else if(totalCalculateAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0 && totalCalculateAmount.compareTo(customerBalanceAmount) > 0) {
			  System.out.println("Condition 5");
			  totalAmountToCalcaulate = totalCalculateAmount.subtract(customerBalanceAmount);
		  }
		  
		  // Display the result in BigDecimal 
		  System.out.println("The sum of\n" +
		  customerBalanceAmount + " \nand\n" + totalCalculateAmount + " " + "\nis\n" +
		  totalAmountToCalcaulate + "\n");
		  
		  }
		 
	


}
