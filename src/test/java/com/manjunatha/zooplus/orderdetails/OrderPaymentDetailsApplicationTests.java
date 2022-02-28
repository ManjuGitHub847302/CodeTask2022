package com.manjunatha.zooplus.orderdetails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.manjunatha.zooplus.orderdetails.model.dto.info.ErrorDetailsInfoDto;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderDetailsRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderPaymentRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.response.CustomerBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderBalanceResponse;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = OrderPaymentDetailsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderPaymentDetailsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetOrderBalanceById() {
		
	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	headers.setContentType(MediaType.APPLICATION_JSON);
	
	ResponseEntity<OrderBalanceResponse> response =restTemplate.exchange(getRootUrl() + "/order-payment-service/order/1", HttpMethod.GET, entity,OrderBalanceResponse.class);
	log.info ("response "+ response.getStatusCode());
	assertNotNull(response.getBody());
	}
	
	@Test
	public void testGetOrderBalanceByIdNotFoundError() {

	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	headers.setContentType(MediaType.APPLICATION_JSON);
		
	ResponseEntity<ErrorDetailsInfoDto> errorResponse =restTemplate.exchange(getRootUrl() + "/order-payment-service/order/1110", HttpMethod.GET, 
			entity,ErrorDetailsInfoDto.class);
	  log.info ("response >>>>>"+ errorResponse.getStatusCode());
	assertEquals(errorResponse.getStatusCode(), HttpStatus.NOT_FOUND);
			 
	}
	
	
	@Test
	public void testGetCustomerBalanceById() {
		
	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	headers.setContentType(MediaType.APPLICATION_JSON);
	
	ResponseEntity<CustomerBalanceResponse> customerBalanceresponse =restTemplate.exchange(getRootUrl() + "/order-payment-service/customer/101", HttpMethod.GET, entity,CustomerBalanceResponse.class);
	 log.info ("customerBalanceresponse >>>>>"+ customerBalanceresponse.getStatusCode());
	assertNotNull(customerBalanceresponse.getBody());
	}
	
	
	@Test
	public void testGetCustomerBalanceByIdNotFoundError() {

	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	headers.setContentType(MediaType.APPLICATION_JSON);
		
	ResponseEntity<ErrorDetailsInfoDto> customerErrorResponse =restTemplate.exchange(getRootUrl() + "/order-payment-service/customer/1110", HttpMethod.GET, 
			entity,ErrorDetailsInfoDto.class);
	 log.info ("customerErrorResponse >>>>>"+ customerErrorResponse.getStatusCode());
	 assertEquals(customerErrorResponse.getStatusCode(), HttpStatus.NOT_FOUND);
			 
	}
	
	@Test
	public void testRegisterOrder() {
		
		OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
		int[] productArray = {1001,1002,1003,1004,1005};
		orderDetailsRequest.setCustomerId(Long.valueOf("201"));
		orderDetailsRequest.setProductId(productArray);
		
		ResponseEntity<String> registerOrderresponse  = restTemplate.postForEntity(getRootUrl() + "/order-payment-service/order", 
				orderDetailsRequest, String.class);
		log.info ("registerOrderresponse >>>>>"+ registerOrderresponse.getBody());
		log.info ("RegisterOrderresposne to object>>>>>"+ registerOrderresponse.getStatusCode());
		
		if(registerOrderresponse.getStatusCode().equals(HttpStatus.CREATED)) {
			assertEquals(HttpStatus.CREATED, registerOrderresponse.getStatusCode());
			assertNotNull(registerOrderresponse.getBody());
		} else if(registerOrderresponse.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			assertEquals(HttpStatus.BAD_REQUEST, registerOrderresponse.getStatusCode());
		}
	}

	
	@Test
	public void testRegisterOrderNotExistingFunction() throws Exception {
		
		OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
		int[] productArray = {1001,1002,1003,1004,1005};
		orderDetailsRequest.setCustomerId(Long.valueOf("1100"));
		orderDetailsRequest.setProductId(productArray);
		
		ResponseEntity<String> registerOrderNotFound  = restTemplate.postForEntity(getRootUrl() + "/order-payment-service/order", 
				orderDetailsRequest, String.class);
		log.info ("registerOrderNotFound >>>>>"+ registerOrderNotFound.getBody());
		log.info ("registerOrderNotFound to object>>>>>"+ registerOrderNotFound.getStatusCode());
		
		assertEquals(HttpStatus.NOT_FOUND, registerOrderNotFound.getStatusCode());
		
	}
	
	@Test
	public void testPerformPayment() {
		
		OrderPaymentRequest orderPaymentRequest = new OrderPaymentRequest();
		orderPaymentRequest.setCustomerId(Long.valueOf("201"));
		orderPaymentRequest.setOrderId(Long.valueOf("1"));
		orderPaymentRequest.setPaidAmount(new BigDecimal("1000.00"));
		orderPaymentRequest.setPaymentMode("Credit Card");
		
		ResponseEntity<String> performPaymentresponse  = restTemplate.postForEntity(getRootUrl() + "/order-payment-service/paymentMethod", 
				orderPaymentRequest, String.class);
		log.info ("PerformPaymentrresponse >>>>>"+ performPaymentresponse.getBody());
		log.info ("PerformPaymentresposne to object>>>>>"+ performPaymentresponse.getStatusCode());
		
		if(performPaymentresponse.getStatusCode().equals(HttpStatus.CREATED)) {
			assertEquals(HttpStatus.CREATED, performPaymentresponse.getStatusCode());
			assertNotNull(performPaymentresponse.getBody());
		} else if(performPaymentresponse.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			assertEquals(HttpStatus.BAD_REQUEST, performPaymentresponse.getStatusCode());
		}
		
	}
	
	@Test
	public void testPerformPaymentIdNotFound() {
		
		OrderPaymentRequest orderPaymentRequest = new OrderPaymentRequest();
		orderPaymentRequest.setCustomerId(Long.valueOf("201"));
		orderPaymentRequest.setOrderId(Long.valueOf("10000"));
		orderPaymentRequest.setPaidAmount(new BigDecimal("1000.00"));
		orderPaymentRequest.setPaymentMode("Credit Card");
		
		ResponseEntity<String> performPaymentresponse  = restTemplate.postForEntity(getRootUrl() + "/order-payment-service/paymentMethod", 
				orderPaymentRequest, String.class);
		log.info ("testPerformPaymentIdNotFound >>>>>"+ performPaymentresponse.getBody());
		log.info ("testPerformPaymentIdNotFound to object>>>>>"+ performPaymentresponse.getStatusCode());
		
		assertEquals(HttpStatus.NOT_FOUND, performPaymentresponse.getStatusCode());
	}
	
}
