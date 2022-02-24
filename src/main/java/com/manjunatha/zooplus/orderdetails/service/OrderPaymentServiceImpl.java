package com.manjunatha.zooplus.orderdetails.service;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manjunatha.zooplus.orderdetails.model.dto.info.OrderDetailsInfoDto;
import com.manjunatha.zooplus.orderdetails.model.dto.info.OrderPaymentInfoDto;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderDetailsRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderPaymentRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.response.CustomerBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderDetailsResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderPaymentResponse;
import com.manjunatha.zooplus.orderdetails.model.persistence.CustomerEntity;
import com.manjunatha.zooplus.orderdetails.model.persistence.OrderDetailsEntity;
import com.manjunatha.zooplus.orderdetails.model.persistence.PaymentEntity;
import com.manjunatha.zooplus.orderdetails.repository.CustomerRepository;
import com.manjunatha.zooplus.orderdetails.repository.OrderDetailsRepository;
import com.manjunatha.zooplus.orderdetails.repository.OrderPaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Autowired
	OrderPaymentRepository orderPaymentRepository;
	
	@Autowired
	CustomerRepository customerBalanceRepository;

	@Override
	public OrderDetailsResponse registerOrder(OrderDetailsRequest orderDetailsRequest) {

		log.info("Entering the OrderDetailsResponse method" + orderDetailsRequest.toString());

		OrderDetailsInfoDto orderDtoToEntity = OrderPaymentServiceUtil.convertOrderDtoEntity(orderDetailsRequest);

		OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity(orderDtoToEntity.getCustomerId(),
				orderDtoToEntity.getProductId(), orderDtoToEntity.getOrderDate(), orderDtoToEntity.getOrderStatus(),
				orderDtoToEntity.getComments(), orderDtoToEntity.getTotalOrderAmount());

		log.info("Entering the orderDetailsEntity before Service orderDetailsEntity");

		try {

			orderDetailsRepository.save(orderDetailsEntity);

		} catch (EntityExistsException exception) {
			log.error("Could not save the Order" + exception.getMessage());
			// throw exception here
		}

		if (orderDetailsEntity == null || orderDetailsEntity.getId() == null
				|| orderDetailsEntity.getId().toString().isEmpty()) {
			log.error("Order Id is null");
			// throw exception here
		}

		log.info("orderDetailsEntity.getId() >>>>" + orderDetailsEntity.getId());
		orderDetailsEntity.setId(orderDetailsEntity.getId());

		return OrderPaymentServiceUtil
				.convertOrderDtoToResponse(OrderPaymentServiceUtil.convertOrderEntityToDto(orderDetailsEntity));

	}

	@Override
	public OrderPaymentResponse registerPayment(OrderPaymentRequest orderPaymentRequest) {

		log.info("Entering the orderPaymentRequest method" + orderPaymentRequest.toString());

		OrderPaymentInfoDto paymentDtoToEntity = OrderPaymentServiceUtil.convertpaymentDtoEntity(orderPaymentRequest);

		log.info("Entering the orderPaymentRequest before Service request");

		PaymentEntity orderPaymentEntity = new PaymentEntity(paymentDtoToEntity.getCustomerId(),
				paymentDtoToEntity.getOrderId(), paymentDtoToEntity.getPaymentDate(),
				paymentDtoToEntity.getPaidAmount(), paymentDtoToEntity.getInvoiceAmount(),
				paymentDtoToEntity.getOrderBalance(), orderPaymentRequest.getPaymentMode());

		log.info("Entering the orderPaymentEntity before Service orderDetailsEntity");

		try {

			orderPaymentRepository.save(orderPaymentEntity);

		} catch (EntityExistsException exception) {
			log.error("Could not save the Order" + exception.getMessage());
			// throw exception here

		}

		if (orderPaymentEntity == null || orderPaymentEntity.getId() == null
				|| orderPaymentEntity.getId().toString().isEmpty()) {
			log.error("payment Order Id is null");
			// throw exception here
		}

		log.info("orderpaymnetEntity.getId() >>>>" + orderPaymentEntity.getId());
		orderPaymentEntity.setId(orderPaymentEntity.getId());

		return OrderPaymentServiceUtil
				.convertPaymentDtoToResponse(OrderPaymentServiceUtil.convertPaymentEntityToDto(orderPaymentEntity));

	}

	@Override
	public OrderPaymentResponse getOrderBalance(String orderId) {
		
		log.info("Entering OrderPaymentResponse getOrderBalance method >>>" + orderId);
		
		//OrderPaymentResponse orderPaymentResponse;
			PaymentEntity paymentEntity;
			
			Optional<PaymentEntity> OrderBalanceDetails = orderPaymentRepository.
					findOrderBalanceByOrderId(Integer.parseInt(orderId));
			
			log.info("Entering OrderPaymentResponse OrderBalanceDetails.isPresent() method >>> " + OrderBalanceDetails.isPresent());
			if(OrderBalanceDetails.isPresent()) {
				 paymentEntity = OrderBalanceDetails.get();
				 //orderPaymentResponse = OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity);
			} else {
				return null;
			}
			
			log.info("Exit Response method >>> " + OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity));
		
		return OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity);
		
	}

	@Override
	public CustomerBalanceResponse getCustomerBalance(String customerId) {
		
		CustomerEntity customerEntity;
		
		log.info("Entering customerId getCustomerBalance method >>>" + customerId);
		
		Optional<CustomerEntity> getCustomerBalance = customerBalanceRepository.findById(Long.parseLong(customerId));
		
		if(getCustomerBalance.isPresent()) {
			customerEntity = getCustomerBalance.get();
			 //orderPaymentResponse = OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity);
		} else {
			return null;
		}
		
		log.info("Exit  getCustomerBalance  method >>>" + customerEntity);
		return OrderPaymentServiceUtil.convertCustomerEntityResponse(customerEntity);
	}

}
