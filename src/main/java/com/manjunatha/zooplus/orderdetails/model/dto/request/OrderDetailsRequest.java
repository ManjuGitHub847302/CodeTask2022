package com.manjunatha.zooplus.orderdetails.model.dto.request;


import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDetailsRequest  {
	
	@NotNull
	private int[] productId;
	
	
	@NotNull
	private String customerId;
	
}
