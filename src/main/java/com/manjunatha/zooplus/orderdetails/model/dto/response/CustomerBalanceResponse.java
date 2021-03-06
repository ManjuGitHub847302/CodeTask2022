package com.manjunatha.zooplus.orderdetails.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CustomerBalanceResponse {
	
	private String customerId;
	
	private String customerBalanceAmount;

}
