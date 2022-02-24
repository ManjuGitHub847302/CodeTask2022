package com.manjunatha.zooplus.orderdetails.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CustomerBalanceResponse {
	
	private String customerId;
	
	private String customerBalanceAmount;

}
