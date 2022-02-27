package com.manjunatha.zooplus.orderdetails.service.util;

import java.math.BigDecimal;

import com.manjunatha.zooplus.orderdetails.model.dto.info.OrderPaymentInfoDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderAllAmountCalcuationUtil {
	
	public static BigDecimal getCalculatedTotalInvoiceAmount(BigDecimal totalPriceInvoiceAmount_TP,
			BigDecimal producutPriceInvoiceAmount_PP, BigDecimal customerBalanceAmount_CB) {
		
		log.info("Entering getCalculatedTotalInvoiceAmount" + "totalPriceInvoiceAmount_TP" + totalPriceInvoiceAmount_TP + "producutPriceInvoiceAmount_PP"+ producutPriceInvoiceAmount_PP +"customerBalanceAmount_CB" + customerBalanceAmount_CB);
		//TP = PP - CB;
		
		if ((customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) == 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0) ||
				 (customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) == 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) < 0)) { 
			  totalPriceInvoiceAmount_TP =producutPriceInvoiceAmount_PP.subtract(customerBalanceAmount_CB); 
		  } else if(producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) == 0 && customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) > 0){
			  totalPriceInvoiceAmount_TP =customerBalanceAmount_CB.subtract(producutPriceInvoiceAmount_PP);
		   }  else if (producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) == 0 && customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) < 0) {
			   totalPriceInvoiceAmount_TP = customerBalanceAmount_CB.negate().subtract(producutPriceInvoiceAmount_PP);
		   } else if(producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) > 0 
				   && producutPriceInvoiceAmount_PP.compareTo(customerBalanceAmount_CB) > 0) {
			   totalPriceInvoiceAmount_TP = producutPriceInvoiceAmount_PP.subtract(customerBalanceAmount_CB);
		   }else if(producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0 && customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) > 0 
				   && customerBalanceAmount_CB.compareTo(producutPriceInvoiceAmount_PP) > 0) {
			   totalPriceInvoiceAmount_TP = customerBalanceAmount_CB.subtract(producutPriceInvoiceAmount_PP);
		   } else if(customerBalanceAmount_CB.compareTo(BigDecimal.ZERO) < 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0){
			   totalPriceInvoiceAmount_TP = producutPriceInvoiceAmount_PP.add(customerBalanceAmount_CB.negate());
		   } else if(customerBalanceAmount_CB.compareTo(producutPriceInvoiceAmount_PP) == 0) {
			   totalPriceInvoiceAmount_TP = producutPriceInvoiceAmount_PP.subtract(customerBalanceAmount_CB);
		   }
		log.info("Entering getCalculatedTotalInvoiceAmount" + "totalPriceInvoiceAmount_TP" + totalPriceInvoiceAmount_TP + "producutPriceInvoiceAmount_PP"+ producutPriceInvoiceAmount_PP +"customerBalanceAmount_CB" + customerBalanceAmount_CB);
		return totalPriceInvoiceAmount_TP;
	}
	
	public static BigDecimal getCalculatedOrderBalance(OrderPaymentInfoDto paymentDtoToEntity) {
		
		BigDecimal orderBalance_OB = BigDecimal.ZERO;
		BigDecimal paidAmount_PA = paymentDtoToEntity.getPaidAmount();
		BigDecimal producutPriceInvoiceAmount_PP = paymentDtoToEntity.getProductsPriceInvoiceAmount();
		//Considering always paid Amount is Positive

		log.info("Entering the getCalculatedOrderBalance" + orderBalance_OB + paidAmount_PA + producutPriceInvoiceAmount_PP);
		//OB = PA-PP;
				if ((paidAmount_PA.compareTo(BigDecimal.ZERO) == 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0)
						|| (producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) == 0 && paidAmount_PA.compareTo(BigDecimal.ZERO) > 0)) {
					orderBalance_OB = paidAmount_PA.subtract(producutPriceInvoiceAmount_PP);
				} else if((paidAmount_PA.compareTo(BigDecimal.ZERO) > 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0 && paidAmount_PA.compareTo(producutPriceInvoiceAmount_PP) > 0)
						|| (paidAmount_PA.compareTo(BigDecimal.ZERO) > 0 && producutPriceInvoiceAmount_PP.compareTo(BigDecimal.ZERO) > 0 && paidAmount_PA.compareTo(producutPriceInvoiceAmount_PP) < 0)) {
					orderBalance_OB = paidAmount_PA.subtract(producutPriceInvoiceAmount_PP);
				} else if(paidAmount_PA.compareTo(producutPriceInvoiceAmount_PP) == 0) {
					orderBalance_OB = paidAmount_PA.subtract(producutPriceInvoiceAmount_PP);
				}
		
		log.info("Exit the getCalculatedOrderBalance" + orderBalance_OB + paidAmount_PA + producutPriceInvoiceAmount_PP);
		return orderBalance_OB;
	}
    
    
	public static BigDecimal getUpdatedCustomerBalance(BigDecimal customerBalance_CB, BigDecimal paidAmount_PA, BigDecimal totalPriceInvoiceAmount_TP) {		
    	
    	log.info("Entering the getUpdatedCustomerBalance" + customerBalance_CB + paidAmount_PA + totalPriceInvoiceAmount_TP);
    		  //CB= PA-TP;
    	
			  if ((paidAmount_PA.compareTo(BigDecimal.ZERO) == 0 && totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) > 0) || 
					 (totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) == 0 && paidAmount_PA.compareTo(BigDecimal.ZERO) > 0) ) { 
				  customerBalance_CB =paidAmount_PA.subtract(totalPriceInvoiceAmount_TP); 
			  } else if((paidAmount_PA.compareTo(BigDecimal.ZERO) > 0 && totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) > 0 && paidAmount_PA.compareTo(totalPriceInvoiceAmount_TP) > 0)
						|| (paidAmount_PA.compareTo(BigDecimal.ZERO) > 0 && totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) > 0 && paidAmount_PA.compareTo(totalPriceInvoiceAmount_TP) < 0)) {
				  customerBalance_CB = paidAmount_PA.subtract(totalPriceInvoiceAmount_TP);
				} else if(totalPriceInvoiceAmount_TP.compareTo(BigDecimal.ZERO) < 0 && paidAmount_PA.compareTo(BigDecimal.ZERO) > 0) {
				  customerBalance_CB = paidAmount_PA.subtract(totalPriceInvoiceAmount_TP.negate());
				} else if(totalPriceInvoiceAmount_TP.compareTo(totalPriceInvoiceAmount_TP) == 0) {
					customerBalance_CB = paidAmount_PA.subtract(totalPriceInvoiceAmount_TP);
				}
			
			  log.info("Exit the getUpdatedCustomerBalance" + customerBalance_CB + paidAmount_PA + totalPriceInvoiceAmount_TP);
		return customerBalance_CB;
		
	}

}
