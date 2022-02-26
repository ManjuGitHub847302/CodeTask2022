package com.manjunatha.zooplus.orderdetails.model.dto.info;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPaymentInfoDto {
	
	private Long paymentId;
	
	private Long customerId;
	
	private Long orderId;
	
	private Date paymentDate;
	
	private BigDecimal paidAmount;
	
	private BigDecimal productsPriceInvoiceAmount;
	
	private BigDecimal orderBalance;
	
	private String paymentMode;

}
