package com.manjunatha.zooplus.orderdetails.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailsResponse {
	
	private String orderId;
	
	private String customerId;
	
	private String totalOrderAmount;
	
	private String orderDate;

	private String orderStatus;

	private String comments;
	
	private String productId;


}
