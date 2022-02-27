package com.manjunatha.zooplus.orderdetails.model.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "OrderDetailsRequest model information")
public class OrderDetailsRequest  {
	
	
	@ApiModelProperty(value = "productId")
	private int[] productId;
	
	
	@ApiModelProperty(value = "customerId")
	private Long customerId;
	
}
