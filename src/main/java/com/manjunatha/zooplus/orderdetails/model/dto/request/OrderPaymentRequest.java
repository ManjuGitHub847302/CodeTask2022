package com.manjunatha.zooplus.orderdetails.model.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor

@ApiModel(description = "OrderPaymentRequest model information")
public class OrderPaymentRequest {
	
	
	@ApiModelProperty(value = "CustomerId")
	private Long customerId;
	
	@ApiModelProperty(value = "Order Id")
	private Long orderId;
	
    @DecimalMin(value = "1.00",message = "Please add Valid Paid Amount and Other Details")
	private BigDecimal paidAmount;
	
    @ApiModelProperty(value = "Payment Mode")
	private String paymentMode;

}
