package com.manjunatha.zooplus.orderdetails;

import java.math.BigDecimal;

public class TestCustomerUpdateBalance {

	public static void main(String[] args) {
		
		
		   	BigDecimal customerBalanceToUpdate = BigDecimal.ZERO;
		  
			  
			   
			  String customerBalance = "-100";
			  String customerOrderBalance = "10000";
			  
			  BigDecimal customerBalanceAmount = new BigDecimal(customerBalance); 
			  BigDecimal customerOrderAmount = new BigDecimal(customerOrderBalance);
			  
			  if (customerBalanceAmount.compareTo(BigDecimal.ZERO) == 0 && customerOrderAmount.compareTo(BigDecimal.ZERO) > 0) { 
				  // Assuming Customer Balance is 0 
				  System.out.println("Condition Sp 1");
				  customerBalanceToUpdate =customerBalanceAmount.add(customerOrderAmount); 
				  customerBalanceToUpdate = customerBalanceToUpdate.negate();
			  } else if (customerBalanceAmount.compareTo(BigDecimal.ZERO) == 0 && customerOrderAmount.compareTo(BigDecimal.ZERO) < 0) { 
				  customerBalanceToUpdate =customerBalanceAmount.add(customerOrderAmount.negate()); 
				  customerBalanceToUpdate = customerBalanceToUpdate.negate();
				  System.out.println("Condition Sp 3");
			  } else if (customerOrderAmount.compareTo(BigDecimal.ZERO) == 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0) { 
				  customerBalanceToUpdate =customerOrderAmount.add(customerBalanceAmount); 
				  System.out.println("Condition Sp 2");
			  }else if (customerOrderAmount.compareTo(BigDecimal.ZERO) == 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) < 0) { 
				  customerBalanceToUpdate =customerOrderAmount.add(customerBalanceAmount.negate()); 
				  customerBalanceToUpdate= customerBalanceToUpdate.negate();
				  System.out.println("Condition Sp 4");
			  } else if (customerOrderAmount.compareTo(BigDecimal.ZERO) < 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) < 0) {
				  System.out.println("Condition 1");
				  customerBalanceToUpdate = customerOrderAmount.negate().add(customerBalanceAmount.negate());
				  customerBalanceToUpdate = customerBalanceToUpdate.negate();
			  } else if(customerOrderAmount.compareTo(BigDecimal.ZERO) < 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0) {
				  System.out.println("Condition 2");
				  customerBalanceToUpdate = customerOrderAmount.negate().subtract(customerBalanceAmount);
				  customerBalanceToUpdate = customerBalanceToUpdate.negate();
			  } else if(customerOrderAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) < 0) {
				  System.out.println("Condition 3");
				  customerBalanceToUpdate = customerOrderAmount.add(customerBalanceAmount.negate());
				  customerBalanceToUpdate =customerBalanceToUpdate.negate();
			  } else if(customerOrderAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(customerOrderAmount) > 0) {
				  System.out.println("Condition 4");
				  customerBalanceToUpdate = customerBalanceAmount.subtract(customerOrderAmount);
			  } else if(customerOrderAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0 && customerOrderAmount.compareTo(customerBalanceAmount) > 0) {
				  System.out.println("Condition 5");
				  customerBalanceToUpdate = customerOrderAmount.subtract(customerBalanceAmount);
				  customerBalanceToUpdate = customerBalanceToUpdate.negate();
			  }
			  
			  System.out.println("The sum of\n" +
					  customerBalanceAmount + " \nand\n" + customerOrderAmount + " " + "\nis\n" +
					  customerBalanceToUpdate + "\n");
					  

	}

}
