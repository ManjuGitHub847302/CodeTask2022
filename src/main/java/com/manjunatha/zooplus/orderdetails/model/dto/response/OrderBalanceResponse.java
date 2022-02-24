package com.manjunatha.zooplus.orderdetails.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBalanceResponse {
	
	private String orderId;
	
	private String orderBalance;
	
}
