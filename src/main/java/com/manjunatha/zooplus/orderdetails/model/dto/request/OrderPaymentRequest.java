package com.manjunatha.zooplus.orderdetails.model.dto.request;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor

public class OrderPaymentRequest {
	
	@NotNull
	private String customerId;
	
	@NotNull
	private String orderId;
	
	@NotNull
	private String paidAmount;
	
	@NotNull
	private String paymentMode;

}
