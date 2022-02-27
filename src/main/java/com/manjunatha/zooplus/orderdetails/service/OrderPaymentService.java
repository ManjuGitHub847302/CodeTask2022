package com.manjunatha.zooplus.orderdetails.service;

import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderDetailsRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderPaymentRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.response.CustomerBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderDetailsResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderPaymentResponse;

public interface OrderPaymentService {
	
	OrderDetailsResponse registerOrder(OrderDetailsRequest orderDetailsRequest);

	OrderPaymentResponse registerPayment(OrderPaymentRequest orderPaymentRequest);

	OrderBalanceResponse getOrderBalance(Long orderId);

	CustomerBalanceResponse getCustomerBalance(Long customerId); 

}
