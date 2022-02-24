package com.manjunatha.zooplus.orderdetails.model.dto.info;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDetailsInfoDto {
	
	private Long orderId;
	
	private Long customerId;
	
	private String productId;

	private Date orderDate;

	private int orderStatus;

	private BigDecimal totalOrderAmount;
	
	private String comments;
	
	private BigDecimal customerPreviousBalance;

}
