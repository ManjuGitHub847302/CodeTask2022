package com.manjunatha.zooplus.orderdetails.api;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order-payment-service") 
@Slf4j
@Api(value = "Order Payment Operation Service")
public class OrderPaymentDetailsController {
	
	@Autowired 
	OrderPaymentService orderPaymentService; 
	
	@ApiOperation(value = "Create order REST API")
	@PostMapping("/order")
	public ResponseEntity<OrderDetailsResponse> registerOrder(@Valid @RequestBody OrderDetailsRequest orderDetailsRequest) {
		
		log.info("Entering the registerOrder method >>>" + orderDetailsRequest);
		OrderDetailsResponse orderDetailsResponse = orderPaymentService.registerOrder(orderDetailsRequest);
		log.info("Exit the registerOrder method >>>>" +orderDetailsResponse.toString());
	    return ResponseEntity.status(HttpStatus.CREATED).body(orderDetailsResponse);
	  }
	
	
	@ApiOperation(value = "Create Payment REST API")
	@PostMapping("/paymentMethod")
	public ResponseEntity<OrderPaymentResponse> registerPayment(@Valid @RequestBody OrderPaymentRequest orderPaymentRequest) {
		
		log.info("Entering the registerOrder method >>>" + orderPaymentRequest);
		OrderPaymentResponse orderPaymentResponse = orderPaymentService.registerPayment(orderPaymentRequest);
		log.info("Exit the registerOrder method >>>>" + orderPaymentResponse);
	    return ResponseEntity.status(HttpStatus.CREATED).body(orderPaymentResponse);
	  }
	
	@ApiOperation(value = "Get an OrderBalance By OrderId REST API")
	@GetMapping("/order/balance/{orderId}")
	public ResponseEntity<OrderBalanceResponse> getOrderBalance(@PathVariable (name="orderId")  @Min(message = "orderId cannot be negative", value = 1) Long orderId) {
		
		log.info("Entering the orderBalance method >>>" + orderId);
		OrderBalanceResponse orderBalanceResponse = orderPaymentService.getOrderBalance(orderId);
		log.info("Exit the orderBalance Resposnse method >>>" + orderBalanceResponse);
		 return ResponseEntity.status(HttpStatus.OK).body(orderBalanceResponse);
	  }
	
	@ApiOperation(value = "Get an CustomerBalance By customerId REST API")
	@GetMapping("/customer/balance/{customerId}")
	public ResponseEntity<CustomerBalanceResponse> getCustomerBalance(@PathVariable (name="customerId")  @Min(message = "CUSTOMER_ID cannot be negative", value = 1) Long customerId) {
		
		log.info("Entering the customerBalanceResponse method >>>" + customerId);
		CustomerBalanceResponse customerBalanceResponse = orderPaymentService.getCustomerBalance(customerId);
		log.info("Exit the customerBalanceResponse Resposnse method >>>" + customerBalanceResponse);
	    return ResponseEntity.status(HttpStatus.OK).body(customerBalanceResponse);
	  
	}
	
	
	

}
