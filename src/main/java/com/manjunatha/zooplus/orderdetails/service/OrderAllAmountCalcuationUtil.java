package com.manjunatha.zooplus.orderdetails.service;

import java.math.BigDecimal;

import com.manjunatha.zooplus.orderdetails.model.dto.info.OrderPaymentInfoDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderAllAmountCalcuationUtil {
	
	static BigDecimal getCalculatedTotalInvoiceAmount(BigDecimal totalInvoiceAmountBalance,
			BigDecimal totalCalculatedProductPriceAmount, BigDecimal customerBalanceAmount) {
		
		log.info("Entering getCalculatedTotalInvoiceAmount" + "getCalculatedTotalInvoiceAmount" + totalInvoiceAmountBalance + "totalCalculatedProductPriceAmount"+ totalCalculatedProductPriceAmount +"customerBalanceAmount" + customerBalanceAmount);
		
		if (customerBalanceAmount.compareTo(BigDecimal.ZERO) == 0 && totalCalculatedProductPriceAmount.compareTo(BigDecimal.ZERO) > 0) { 
			  // Assuming Customer Balance is 0 
			  totalInvoiceAmountBalance =customerBalanceAmount.add(totalCalculatedProductPriceAmount); 
			  totalInvoiceAmountBalance = totalInvoiceAmountBalance.negate();
		  } else if (customerBalanceAmount.compareTo(BigDecimal.ZERO) == 0 && totalCalculatedProductPriceAmount.compareTo(BigDecimal.ZERO) < 0) { 
			  totalInvoiceAmountBalance =customerBalanceAmount.add(totalCalculatedProductPriceAmount.negate()); 
			  totalInvoiceAmountBalance = totalInvoiceAmountBalance.negate();
		  } else if (totalCalculatedProductPriceAmount.compareTo(BigDecimal.ZERO) == 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0) { 
			  totalInvoiceAmountBalance =customerBalanceAmount.add(totalCalculatedProductPriceAmount); 
		  } else if (totalCalculatedProductPriceAmount.compareTo(BigDecimal.ZERO) < 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) < 0) {
			  totalInvoiceAmountBalance = totalCalculatedProductPriceAmount.negate().add(customerBalanceAmount.negate());
			  totalInvoiceAmountBalance = totalCalculatedProductPriceAmount.negate();
		  } else if(totalCalculatedProductPriceAmount.compareTo(BigDecimal.ZERO) < 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0) {
			  totalInvoiceAmountBalance = totalCalculatedProductPriceAmount.negate().subtract(customerBalanceAmount);
			  totalInvoiceAmountBalance = totalInvoiceAmountBalance.negate();
		  } else if(totalCalculatedProductPriceAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) < 0) {
			  totalInvoiceAmountBalance = totalCalculatedProductPriceAmount.add(customerBalanceAmount.negate());
			  totalInvoiceAmountBalance =totalInvoiceAmountBalance.negate();
		  } else if(totalCalculatedProductPriceAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(totalCalculatedProductPriceAmount) > 0) {
			  totalInvoiceAmountBalance = customerBalanceAmount.subtract(totalCalculatedProductPriceAmount);
		  } else if(totalCalculatedProductPriceAmount.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount.compareTo(BigDecimal.ZERO) > 0 && totalCalculatedProductPriceAmount.compareTo(customerBalanceAmount) > 0) {
			  totalInvoiceAmountBalance = totalCalculatedProductPriceAmount.subtract(customerBalanceAmount);
		  }
		log.info("Exit method" + "getCalculatedTotalInvoiceAmount" + totalInvoiceAmountBalance + "totalCalculatedProductPriceAmount"+ totalCalculatedProductPriceAmount +"customerBalanceAmount" + customerBalanceAmount);
		return totalInvoiceAmountBalance;
	}
	
    static BigDecimal getCalculatedOrderBalance(OrderPaymentInfoDto paymentDtoToEntity) {
		
		BigDecimal orderBalance = BigDecimal.ZERO;
		BigDecimal paidAmount = paymentDtoToEntity.getPaidAmount();
		BigDecimal invoiceAmount = paymentDtoToEntity.getInvoiceAmount();
		//Considering always paid Amount is Positive

		log.info("Entering the getCalculatedOrderBalance" + orderBalance + paidAmount + invoiceAmount);
		if (invoiceAmount.compareTo(BigDecimal.ZERO) == 0 && paidAmount.compareTo(BigDecimal.ZERO) > 0) {
			// Assuming Customer Balance is 0
				orderBalance = paidAmount.add(invoiceAmount);
			} else if (invoiceAmount.compareTo(BigDecimal.ZERO) == 0 && paidAmount.compareTo(BigDecimal.ZERO) < 0) {
				orderBalance = invoiceAmount.add(paidAmount.negate());
				orderBalance = orderBalance.negate();
			} else if (paidAmount.compareTo(BigDecimal.ZERO) == 0 && invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {
				orderBalance = paidAmount.add(invoiceAmount.negate());
				orderBalance = orderBalance.negate();
			}else if (paidAmount.compareTo(BigDecimal.ZERO) == 0 && invoiceAmount.compareTo(BigDecimal.ZERO) > 0) {
				orderBalance = paidAmount.add(invoiceAmount.negate());
			} else if(paidAmount.compareTo(BigDecimal.ZERO)< 0 && invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {
				orderBalance = paidAmount.negate().add(invoiceAmount.negate());
				orderBalance = orderBalance.negate();
			} else if(paidAmount.compareTo(BigDecimal.ZERO)< 0 && invoiceAmount.compareTo(BigDecimal.ZERO)> 0) {
				orderBalance = invoiceAmount.add(paidAmount.negate());
				orderBalance = orderBalance.negate();
			} else if(paidAmount.compareTo(BigDecimal.ZERO)> 0 && invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {
				orderBalance = invoiceAmount.negate().subtract(paidAmount);
				orderBalance = orderBalance.negate();
			} else if(paidAmount.compareTo(BigDecimal.ZERO)> 0 && invoiceAmount.compareTo(BigDecimal.ZERO) > 0 && paidAmount.compareTo(invoiceAmount) > 0) {
				orderBalance = paidAmount.subtract(invoiceAmount);
			} else if(paidAmount.compareTo(BigDecimal.ZERO)> 0 && invoiceAmount.compareTo(BigDecimal.ZERO) > 0 && invoiceAmount.compareTo(paidAmount) > 0) {
				orderBalance = invoiceAmount.subtract(paidAmount);
				orderBalance = orderBalance.negate();
			}
		
		log.info("Exit the getCalculatedOrderBalance" + orderBalance + paidAmount + invoiceAmount);
		return orderBalance;
	}
    
    
    static BigDecimal getUpdatedCustomerBalance(BigDecimal customerBalanceAmount, BigDecimal customerOrderAmount,BigDecimal customerBalanceToUpdate) {
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
		return customerBalanceToUpdate;
		
	}

}
