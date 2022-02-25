package com.manjunatha.zooplus.orderdetails;

import java.math.BigDecimal;

public class TestOrderBalance {

	public static void main(String[] args) {
		
		BigDecimal orderBalance = BigDecimal.ZERO;

		BigDecimal paidAmount = new BigDecimal("0");
		BigDecimal invoiceAmount = new BigDecimal("10");
		//Considering always paid Amount is Positive

		if (invoiceAmount.compareTo(BigDecimal.ZERO) == 0 && paidAmount.compareTo(BigDecimal.ZERO) > 0) {
			// Assuming Customer Balance is 0
				orderBalance = paidAmount.add(invoiceAmount);
			} else if (invoiceAmount.compareTo(BigDecimal.ZERO) == 0 && paidAmount.compareTo(BigDecimal.ZERO) < 0) {
				orderBalance = invoiceAmount.add(paidAmount.negate());
				orderBalance = orderBalance.negate();
				System.out.println("Condition SP1");
			} else if (paidAmount.compareTo(BigDecimal.ZERO) == 0 && invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {
				orderBalance = paidAmount.add(invoiceAmount.negate());
				orderBalance = orderBalance.negate();
				System.out.println("Condition SP2");
			}else if (paidAmount.compareTo(BigDecimal.ZERO) == 0 && invoiceAmount.compareTo(BigDecimal.ZERO) > 0) {
				orderBalance = paidAmount.add(invoiceAmount.negate());
				System.out.println("Condition SP3");
			} else if(paidAmount.compareTo(BigDecimal.ZERO)< 0 && invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {
				System.out.println("Condition 1");
				orderBalance = paidAmount.negate().add(invoiceAmount.negate());
				orderBalance = orderBalance.negate();
			} else if(paidAmount.compareTo(BigDecimal.ZERO)< 0 && invoiceAmount.compareTo(BigDecimal.ZERO)> 0) {
				System.out.println("Condition 2");
				orderBalance = invoiceAmount.add(paidAmount.negate());
				orderBalance = orderBalance.negate();
			} else if(paidAmount.compareTo(BigDecimal.ZERO)> 0 && invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {
				System.out.println("Condition 3");
				orderBalance = invoiceAmount.negate().subtract(paidAmount);
				orderBalance = orderBalance.negate();
			} else if(paidAmount.compareTo(BigDecimal.ZERO)> 0 && invoiceAmount.compareTo(BigDecimal.ZERO) > 0 && paidAmount.compareTo(invoiceAmount) > 0) {
				System.out.println("Condition 4");
				orderBalance = paidAmount.subtract(invoiceAmount);
			} else if(paidAmount.compareTo(BigDecimal.ZERO)> 0 && invoiceAmount.compareTo(BigDecimal.ZERO) > 0 && invoiceAmount.compareTo(paidAmount) > 0) {
				System.out.println("Condition 5");
				orderBalance = invoiceAmount.subtract(paidAmount);
				orderBalance = orderBalance.negate();
			}
		// Display the result in BigDecimal
		System.out.println("The sum of\n" + paidAmount + " \nand\n" + invoiceAmount + " " + "\nis\n"
				+ orderBalance + "\n");

	}

}
