package com.manjunatha.zooplus.orderdetails.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderDetailsRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderPaymentRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.response.CustomerBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderDetailsResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderPaymentResponse;
import com.manjunatha.zooplus.orderdetails.service.OrderPaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order-payment-service")
@Slf4j
public class OrderPaymentDetailsController {
	
	@Autowired 
	OrderPaymentService orderPaymentService;
	
	//Rest Service 1: Post :Service to register an order
	@PostMapping("/registerorder")
	public ResponseEntity<OrderDetailsResponse> registerOrder(@RequestBody OrderDetailsRequest orderDetailsRequest) {
		
		log.info("Entering the registerOrder method >>>" + orderDetailsRequest);
		OrderDetailsResponse orderDetailsResponse = orderPaymentService.registerOrder(orderDetailsRequest);
		log.info("Exit the registerOrder method >>>>" +orderDetailsResponse.toString());
	    return ResponseEntity.status(HttpStatus.CREATED).body(orderDetailsResponse);
	  }
	
	
	//Rest Service 2: Post : Service to register the payment of order
	
	@PostMapping("/registerpayment")
	public ResponseEntity<OrderPaymentResponse> registerPayment(@RequestBody OrderPaymentRequest orderPaymentRequest) {
		
		log.info("Entering the registerOrder method >>>" + orderPaymentRequest);
		OrderPaymentResponse orderPaymentResponse = orderPaymentService.registerPayment(orderPaymentRequest);
		log.info("Exit the registerOrder method >>>>" + orderPaymentResponse);
	    return ResponseEntity.status(HttpStatus.CREATED).body(orderPaymentResponse);
	  }
	
	//Rest Service 3: Get : Service to get the order balance
	@GetMapping("/getorderbalance/orderId/{orderId}")
	public ResponseEntity<OrderBalanceResponse> getOrderBalance(@PathVariable (name="orderId") String orderId) {
		
		log.info("Entering the orderBalance method >>>" + orderId);
		OrderBalanceResponse orderBalanceResponse = orderPaymentService.getOrderBalance(orderId);
		log.info("Exit the orderBalance Resposnse method >>>" + orderBalanceResponse);
		 return ResponseEntity.status(HttpStatus.OK).body(orderBalanceResponse);
	  }
	
	//Rest Service 4: Get : Service to get the Customer balance
	@GetMapping("/getcustomerbalance/customerId/{customerId}")
	public ResponseEntity<CustomerBalanceResponse> getCustomerBalance(@PathVariable (name="customerId") String customerId) {
		
		log.info("Entering the customerBalanceResponse method >>>" + customerId);
		CustomerBalanceResponse customerBalanceResponse = orderPaymentService.getCustomerBalance(customerId);
		log.info("Exit the customerBalanceResponse Resposnse method >>>" + customerBalanceResponse);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(customerBalanceResponse);
	  }
	
	
	

}
