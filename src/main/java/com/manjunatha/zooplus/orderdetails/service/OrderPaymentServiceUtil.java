package com.manjunatha.zooplus.orderdetails.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.manjunatha.zooplus.orderdetails.model.dto.info.OrderDetailsInfoDto;
import com.manjunatha.zooplus.orderdetails.model.dto.info.OrderPaymentInfoDto;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderDetailsRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderPaymentRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.response.CustomerBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderDetailsResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderPaymentResponse;
import com.manjunatha.zooplus.orderdetails.model.persistence.CustomerEntity;
import com.manjunatha.zooplus.orderdetails.model.persistence.OrderDetailsEntity;
import com.manjunatha.zooplus.orderdetails.model.persistence.PaymentEntity;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class OrderPaymentServiceUtil {
	
	public static OrderDetailsInfoDto convertOrderDtoEntity(OrderDetailsRequest orderDetailsRequest) {
		
		OrderDetailsInfoDto orderDtoToEntity = new OrderDetailsInfoDto();
		String orderProductId = Arrays.stream(orderDetailsRequest.getProductId()).mapToObj(String::valueOf).
				reduce((a, b) -> a.concat(",").concat(b)).get();
		orderDtoToEntity.setProductId(orderProductId);
		orderDtoToEntity.setCustomerId(Long.valueOf(orderDetailsRequest.getCustomerId()));
		orderDtoToEntity.setOrderDate(new Date());
		orderDtoToEntity.setComments("Order Created Sucessfully");
		orderDtoToEntity.setOrderStatus(1);
		return orderDtoToEntity;
	}
	
	 public static OrderDetailsInfoDto convertOrderEntityToDto(OrderDetailsEntity orderDetailsEntity) {
		
		OrderDetailsInfoDto orderEntityToDto = new OrderDetailsInfoDto();
		orderEntityToDto.setOrderId(orderDetailsEntity.getId());
		orderEntityToDto.setProductId(orderDetailsEntity.getProductId());
		orderEntityToDto.setCustomerId(orderDetailsEntity.getCustomerId());
		orderEntityToDto.setProductsPriceInvoiceAmount(orderDetailsEntity.getProductsPriceInvoiceAmount());
		orderEntityToDto.setTotalProductsPriceInvoiceAmount(orderDetailsEntity.getTotalProductsPriceInvoiceAmount());
		orderEntityToDto.setOrderDate(orderDetailsEntity.getOrderDate());
		orderEntityToDto.setComments(orderDetailsEntity.getComments());
		orderEntityToDto.setOrderStatus(orderDetailsEntity.getOrderStatus());
		return orderEntityToDto;
	 }

	public static OrderDetailsResponse convertOrderDtoToResponse(OrderDetailsInfoDto orderEntityToDto,String getCustomerPreviousBalance) {
		
		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		orderDetailsResponse.setProductId(orderEntityToDto.getProductId());
		orderDetailsResponse.setOrderId(orderEntityToDto.getOrderId().toString());
		orderDetailsResponse.setComments(orderEntityToDto.getComments());
		orderDetailsResponse.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").format(orderEntityToDto.getOrderDate()));
		orderDetailsResponse.setProductsPriceInvoiceAmount(orderEntityToDto.getProductsPriceInvoiceAmount().toString());
		orderDetailsResponse.setTotalProductsPriceInvoiceAmount(orderEntityToDto.getTotalProductsPriceInvoiceAmount().toString());
		orderDetailsResponse.setOrderStatus(String.valueOf(orderEntityToDto.getOrderStatus()));
		orderDetailsResponse.setCustomerId(orderEntityToDto.getCustomerId().toString());
		orderDetailsResponse.setCustomerPreviousBalance(getCustomerPreviousBalance);
		return orderDetailsResponse;
	}

	public static OrderPaymentInfoDto convertpaymentDtoEntity(OrderPaymentRequest orderPaymentRequest) {
		
		OrderPaymentInfoDto orderPaymentInfoDto = new OrderPaymentInfoDto();
		orderPaymentInfoDto.setCustomerId(Long.parseLong(orderPaymentRequest.getCustomerId()));
		orderPaymentInfoDto.setOrderId(Long.parseLong(orderPaymentRequest.getOrderId()));
		orderPaymentInfoDto.setPaidAmount(new BigDecimal(orderPaymentRequest.getPaidAmount()));
		orderPaymentInfoDto.setPaymentDate(new Date());
		orderPaymentInfoDto.setPaymentMode(orderPaymentRequest.getPaymentMode());
		return orderPaymentInfoDto;
	}

	

	public static OrderPaymentInfoDto convertPaymentEntityToDto(PaymentEntity orderPaymentEntity) {
		OrderPaymentInfoDto orderPaymentInfoDto = new OrderPaymentInfoDto();
		orderPaymentInfoDto.setPaymentId(orderPaymentEntity.getId());
		orderPaymentInfoDto.setOrderId(orderPaymentEntity.getOrderId());
		orderPaymentInfoDto.setCustomerId(orderPaymentEntity.getCustomerId());
		orderPaymentInfoDto.setPaidAmount(orderPaymentEntity.getPaidAmount());
		orderPaymentInfoDto.setProductsPriceInvoiceAmount(orderPaymentEntity.getProductsPriceInvoiceAmount());
		orderPaymentInfoDto.setPaymentDate(orderPaymentEntity.getPaymentDate());
		orderPaymentInfoDto.setPaymentMode(orderPaymentEntity.getPaymentMode());
		orderPaymentInfoDto.setOrderBalance(orderPaymentEntity.getOrderBalance());
		return orderPaymentInfoDto;
	}

	public static OrderPaymentResponse convertPaymentDtoToResponse(OrderPaymentInfoDto orderPaymentEntityToDto) {
		
		OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse();
		orderPaymentResponse.setCustomerId(String.valueOf(orderPaymentEntityToDto.getCustomerId()));
		orderPaymentResponse.setPaymnetId(orderPaymentEntityToDto.getPaymentId().toString());
		orderPaymentResponse.setOrderId(String.valueOf(orderPaymentEntityToDto.getOrderId()));
		orderPaymentResponse.setPaidAmount(orderPaymentEntityToDto.getPaidAmount().toString());
		orderPaymentResponse.setPaymentDate(new SimpleDateFormat("yyyy-MM-dd").format(orderPaymentEntityToDto.getPaymentDate()));
		orderPaymentResponse.setProductsPriceInvoiceAmount(orderPaymentEntityToDto.getProductsPriceInvoiceAmount().toString());
		orderPaymentResponse.setPaymentMode(orderPaymentEntityToDto.getPaymentMode());
		orderPaymentResponse.setOrderBalance(orderPaymentEntityToDto.getOrderBalance().toString());
		return orderPaymentResponse;
	}

	public static OrderBalanceResponse convertPaymentEntityResponse(PaymentEntity paymentEntity) {
		OrderBalanceResponse orderbalanceResponse = new OrderBalanceResponse();
		orderbalanceResponse.setOrderBalance(paymentEntity.getOrderBalance().toString());
		orderbalanceResponse.setOrderId(String.valueOf(paymentEntity.getOrderId()));
		return orderbalanceResponse;
	}

	public static CustomerBalanceResponse convertCustomerEntityResponse(CustomerEntity customerEntity) {
		CustomerBalanceResponse customerBalanceResponse = new CustomerBalanceResponse();
		customerBalanceResponse.setCustomerId(customerEntity.getId().toString());
		customerBalanceResponse.setCustomerBalanceAmount(customerEntity.getCustomerBalance().toString());
		return customerBalanceResponse;
	}

	
}
