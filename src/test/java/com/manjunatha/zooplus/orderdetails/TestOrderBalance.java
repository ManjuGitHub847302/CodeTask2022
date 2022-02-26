package com.manjunatha.zooplus.orderdetails;

import java.math.BigDecimal;

public class TestOrderBalance {

	public static void main(String[] args) {
		
		BigDecimal orderBalance_OB = BigDecimal.ZERO;
		BigDecimal paidAmount_PA = new BigDecimal("10");
		BigDecimal producutPriceInvoiceAmount_PP = new BigDecimal("100");
		//Considering always paid Amount is Positive
		//OB = PA-PP;
		

		if ((paidAmount_PA.compareTo(BigDecimal.ZERO) == 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0)
				|| (producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) == 0 && paidAmount_PA.compareTo(BigDecimal.ZERO) > 0)) {
			System.out.println("Condition SP1");
			orderBalance_OB = paidAmount_PA.subtract(producutPriceInvoiceAmount_PP);
		} else if((paidAmount_PA.compareTo(BigDecimal.ZERO) > 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0 && paidAmount_PA.compareTo(producutPriceInvoiceAmount_PP) > 0)
				|| (paidAmount_PA.compareTo(BigDecimal.ZERO) > 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0 && paidAmount_PA.compareTo(producutPriceInvoiceAmount_PP) < 0)) {
			orderBalance_OB = paidAmount_PA.subtract(producutPriceInvoiceAmount_PP);
		} 
		// Display the result in BigDecimal
		System.out.println("The sum of\n" + paidAmount_PA + " \nand\n" + producutPriceInvoiceAmount_PP + " " + "\nis\n"
				+ orderBalance_OB + "\n");

	}

}
