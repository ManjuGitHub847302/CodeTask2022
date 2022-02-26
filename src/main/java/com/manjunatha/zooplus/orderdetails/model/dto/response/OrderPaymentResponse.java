package com.manjunatha.zooplus.orderdetails.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class OrderPaymentResponse {
	
	private String paymnetId;
	
	private String customerId;
	
	private String orderId;
	
	private String paidAmount;
	
	private String paymentDate;
	
	private String productsPriceInvoiceAmount;
	
	private String paymentMode;
	
	private String orderBalance;
	

}
