package com.manjunatha.zooplus.orderdetails;

import java.math.BigDecimal;

public class TestTotalPriceInvoiceAmount {

	public static void main(String[] args) {

		  
		  BigDecimal totalPriceInvoiceAmount_TP = BigDecimal.ZERO;
		  BigDecimal producutPriceInvoiceAmount_PP = new BigDecimal("1000"); 
		  BigDecimal customerBalanceAmount_CB = new BigDecimal("-100");
		  
		  
		  //TP = PP - CB;
		  
		  if ((customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) == 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0) ||
				 (customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) == 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) < 0)) { 
			  System.out.println("Condition 1");
			  totalPriceInvoiceAmount_TP =producutPriceInvoiceAmount_PP.subtract(customerBalanceAmount_CB); 
		  } else if(producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) == 0 && customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) > 0){
			  System.out.println("Condition 2");
			  totalPriceInvoiceAmount_TP =customerBalanceAmount_CB.subtract(producutPriceInvoiceAmount_PP);
		   }  else if (producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) == 0 && customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) < 0) {
			   System.out.println("Condition 3");
			   totalPriceInvoiceAmount_TP = customerBalanceAmount_CB.subtract(producutPriceInvoiceAmount_PP);
		   } else if(producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) > 0 
				   && producutPriceInvoiceAmount_PP.compareTo(customerBalanceAmount_CB) > 0) {
			   System.out.println("Condition 4");
			   totalPriceInvoiceAmount_TP = producutPriceInvoiceAmount_PP.subtract(customerBalanceAmount_CB);
		   }else if(producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) > 0 
				   && customerBalanceAmount_CB.compareTo(producutPriceInvoiceAmount_PP) > 0) {
			   System.out.println("Condition 5");
			   totalPriceInvoiceAmount_TP = customerBalanceAmount_CB.subtract(producutPriceInvoiceAmount_PP);
		   } else if(customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) < 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0){
			   System.out.println("Condition 6");
			   totalPriceInvoiceAmount_TP = producutPriceInvoiceAmount_PP.add(customerBalanceAmount_CB.negate());
			   totalPriceInvoiceAmount_TP = totalPriceInvoiceAmount_TP.negate();
		   }
			  
		  // Display the result in BigDecimal 
		  System.out.println("The sum of\n" +
				  producutPriceInvoiceAmount_PP + " \nand\n" + customerBalanceAmount_CB + " " + "\nis\n" +
				  totalPriceInvoiceAmount_TP + "\n");
		  
		  }
		 
}
