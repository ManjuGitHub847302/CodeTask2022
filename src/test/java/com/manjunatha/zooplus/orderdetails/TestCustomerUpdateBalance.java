package com.manjunatha.zooplus.orderdetails;

import java.math.BigDecimal;

public class TestCustomerUpdateBalance {

	public static void main(String[] args) {
		
		   	  BigDecimal customerBalance_CB = BigDecimal.ZERO;
		   	  
			  BigDecimal paidAmount_PA = new BigDecimal("1000"); 
			  BigDecimal totalPriceInvoiceAmount_TP = new BigDecimal("100");
			  
			  //CB= PA-TP;
			  
			  if ((paidAmount_PA.compareTo(BigDecimal.ZERO) == 0 && totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) > 0) || 
					 (totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) == 0 && paidAmount_PA.compareTo(BigDecimal.ZERO) > 0) ) { 
				  System.out.println("Condition Sp 1");
				  customerBalance_CB =paidAmount_PA.subtract(totalPriceInvoiceAmount_TP); 
			  } else if((paidAmount_PA.compareTo(BigDecimal.ZERO) > 0 && totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) > 0 && paidAmount_PA.compareTo(totalPriceInvoiceAmount_TP) > 0)
						|| (paidAmount_PA.compareTo(BigDecimal.ZERO) > 0 && totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) > 0 && paidAmount_PA.compareTo(totalPriceInvoiceAmount_TP) < 0)) {
				  customerBalance_CB = paidAmount_PA.subtract(totalPriceInvoiceAmount_TP);
				} else if(totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) < 0 && paidAmount_PA.compareTo(BigDecimal.ZERO) > 0) {
				  customerBalance_CB = paidAmount_PA.subtract(totalPriceInvoiceAmount_TP.negate());
				} else if(totalPriceInvoiceAmount_TP.compareTo(totalPriceInvoiceAmount_TP) == 0) {
					  System.out.println("Condition Sp 4");
				customerBalance_CB = paidAmount_PA.subtract(totalPriceInvoiceAmount_TP);
				}
			  
			  System.out.println("The sum of\n" +
					  paidAmount_PA + " \nand\n" + totalPriceInvoiceAmount_TP + " " + "\nis\n" +
					  customerBalance_CB + "\n");
					  

	}

}
