package com.manjunatha.zooplus.orderdetails.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.manjunatha.zooplus.orderdetails.model.persistence.ProductEntity;
import com.manjunatha.zooplus.orderdetails.repository.CustomerRepository;
import com.manjunatha.zooplus.orderdetails.repository.OrderDetailsRepository;
import com.manjunatha.zooplus.orderdetails.repository.OrderPaymentRepository;
import com.manjunatha.zooplus.orderdetails.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Autowired
	OrderPaymentRepository orderPaymentRepository;

	@Autowired
	CustomerRepository customerDetailsRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public OrderDetailsResponse registerOrder(OrderDetailsRequest orderDetailsRequest) {

		log.info("Entering the OrderDetailsResponse method" + orderDetailsRequest.toString());

		OrderDetailsInfoDto orderDtoToEntity = OrderPaymentServiceUtil.convertOrderDtoEntity(orderDetailsRequest);

		// Calculate logic for getTotalOrderAmount Start

		String getCustomerPreviousBalance = "";

		BigDecimal totalOrderInvoiceAmountBalance = BigDecimal.ZERO;
		
		getOrderTotalAmount(orderDtoToEntity, getCustomerPreviousBalance,totalOrderInvoiceAmountBalance);
		
		if(getCustomerPreviousBalance.isEmpty() || getCustomerPreviousBalance == null) {
			CustomerEntity customerEntity = getCustomerDetailsById(orderDtoToEntity.getCustomerId());
			getCustomerPreviousBalance = customerEntity.getCustomerBalance().toString();
		}

		log.info("getCustomerPreviousBalance" + getCustomerPreviousBalance);

		// Calculate logic for getTotalOrderAmount End

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

		return OrderPaymentServiceUtil.convertOrderDtoToResponse(
				OrderPaymentServiceUtil.convertOrderEntityToDto(orderDetailsEntity), getCustomerPreviousBalance);

	}

	@Override
	public OrderPaymentResponse registerPayment(OrderPaymentRequest orderPaymentRequest) {

		log.info("Entering the orderPaymentRequest method" + orderPaymentRequest.toString());
		OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse();

		OrderPaymentInfoDto paymentDtoToEntity = OrderPaymentServiceUtil.convertpaymentDtoEntity(orderPaymentRequest);
		

		log.info("Entering the orderPaymentRequest before Service request");

		// Logic to get Invoice Amount logic Start
		OrderDetailsEntity orderDetailsEntity;
		try {

			Optional<OrderDetailsEntity> orderInvoiceAmountEntity = orderDetailsRepository.findById(paymentDtoToEntity.getOrderId());

			log.info("Entering OrderPaymentResponse OrderBalanceDetails.isPresent() method >>> " + orderInvoiceAmountEntity.isPresent());
			if (orderInvoiceAmountEntity.isPresent()) {
				orderDetailsEntity = orderInvoiceAmountEntity.get();
				paymentDtoToEntity.setInvoiceAmount(orderDetailsEntity.getTotalOrderAmount());
			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// Logic to get Invoice Amount logic Ends
		
		//Calculate Order balance Amount Starts
		
		if(orderPaymentRequest.getPaidAmount()!=null && paymentDtoToEntity.getInvoiceAmount()!=null) {
			
			log.info("Entering Order Balance Logic orderPaymentRequest.getPaidAmount() "+orderPaymentRequest.getPaidAmount() 
			+"paymentDtoToEntity.getInvoiceAmount()" + paymentDtoToEntity.getInvoiceAmount());
			
			BigDecimal getCalculatedOrderBalance = OrderAllAmountCalcuationUtil.getCalculatedOrderBalance(paymentDtoToEntity);
			
			log.info("getCalculatedOrderBalance Order Balance Logic orderPaymentRequest.getPaidAmount()"+ getCalculatedOrderBalance);
			
			if(getCalculatedOrderBalance!=null) {
				paymentDtoToEntity.setOrderBalance(getCalculatedOrderBalance);
			}
		}
		
		//Calculate Order balance Amount Ends
		

		//Logic to Calculate UpdateCustomerbalance Starts
		
			BigDecimal customerBalanceToUpdate = BigDecimal.ZERO;
			
		 if(paymentDtoToEntity.getCustomerId()!=null && paymentDtoToEntity.getOrderBalance()!=null) {
			 
			 BigDecimal customerBalanceAmount = BigDecimal.ZERO;
			 BigDecimal customerOrderAmount = paymentDtoToEntity.getOrderBalance();
			 
			 CustomerEntity customerEntity;
			 
				try {
					Optional<CustomerEntity> getCustomerBalanceEntity = customerDetailsRepository.findById(paymentDtoToEntity.getCustomerId());
					log.info("Entering getCustomerBalanceEntity getCustomerBalanceEntity >>> " + getCustomerBalanceEntity.toString() + "getCustomerBalanceEntity" + getCustomerBalanceEntity);
					if (getCustomerBalanceEntity.isPresent()) {
						customerEntity = getCustomerBalanceEntity.get();
						customerBalanceAmount = customerEntity.getCustomerBalance();
						
						log.info("getUpdatedCustomerBalance Before>>>" + customerBalanceAmount + " "+ customerOrderAmount  + " " +customerBalanceToUpdate);
						customerBalanceToUpdate =  OrderAllAmountCalcuationUtil.getUpdatedCustomerBalance(customerBalanceAmount, customerOrderAmount,customerBalanceToUpdate);
						log.info("getUpdatedCustomerBalance End<<<<" + customerBalanceAmount + " "+customerOrderAmount  + " " + customerBalanceToUpdate);
					 
						try {
							 if(customerBalanceToUpdate!=null) {
								 log.info("getUpdatedCustomerBalance Before>>>" +customerBalanceToUpdate + "getCustomerBalanceEntity" +getCustomerBalanceEntity);
								 customerEntity.setCustomerBalance(customerBalanceToUpdate);
								 customerDetailsRepository.save(customerEntity);
								 log.info("getUpdatedCustomerBalance Exit>>>" +customerBalanceToUpdate);
							 }
							
							} catch (Exception e) {
								// TODO: handle exception
							}
						
					} else {
						return null;
					}

				} catch (Exception e) {
					
				}
				
		 }
		 
		
		//Logic to Calculate UpdateCustomerbalance Ends
		
		 PaymentEntity orderPaymentEntity;

			Optional<PaymentEntity> OrderBalanceDetails = orderPaymentRepository.findOrderBalanceByOrderId(paymentDtoToEntity.getOrderId());

			log.info("Entering OrderPaymentResponse OrderBalanceDetails.isPresent() method >>> "
					+ OrderBalanceDetails.isPresent());
			if (!OrderBalanceDetails.isPresent()) {
				// orderPaymentResponse =
				// OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity);
				
				 orderPaymentEntity = new PaymentEntity(paymentDtoToEntity.getCustomerId(),
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
				
			} else {
				log.error("Payment Cannot be done for the same order Id");
			}
			
			return orderPaymentResponse;
		 
	}

	

	

	@Override
	public OrderBalanceResponse getOrderBalance(String orderId) {

		log.info("Entering OrderPaymentResponse getOrderBalance method >>>" + orderId);

		// OrderPaymentResponse orderPaymentResponse;
		PaymentEntity paymentEntity;

		Optional<PaymentEntity> OrderBalanceDetails = orderPaymentRepository
				.findOrderBalanceByOrderId(Long.parseLong(orderId));

		log.info("Entering OrderPaymentResponse OrderBalanceDetails.isPresent() method >>> "
				+ OrderBalanceDetails.isPresent());
		if (OrderBalanceDetails.isPresent()) {
			paymentEntity = OrderBalanceDetails.get();
			// orderPaymentResponse =
			// OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity);
		} else {
			return null;
		}

		log.info("Exit Response method >>> " + OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity));

		return OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity);

	}

	@Override
	public CustomerBalanceResponse getCustomerBalance(String customerId) {

		log.info("Entering customerId getCustomerBalance method >>>" + customerId);

		CustomerEntity customerEntity = getCustomerDetailsById(Long.parseLong(customerId));

		log.info("Exit  getCustomerBalance  method >>>" + customerEntity);

		return OrderPaymentServiceUtil.convertCustomerEntityResponse(customerEntity);
	}

	private void getOrderTotalAmount(OrderDetailsInfoDto orderDtoToEntity, String getCustomerPreviousBalance, BigDecimal totalOrderInvoiceAmountBalance) {

		List<String> productIdList = Stream.of(orderDtoToEntity.getProductId().split(",")).collect(Collectors.toList());

		BigDecimal totalCalculatedProductPriceAmount;
		BigDecimal customerBalanceAmount;

		try {

			List<ProductEntity> productPriceListDetails = productRepository.findtheProductPriceByProductId(productIdList);

			List<BigDecimal> productPriceList = productPriceListDetails.stream().map(ProductEntity::getProductPrice)
					.collect(Collectors.toList());

			totalCalculatedProductPriceAmount = productPriceList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

			log.info("totalCalculatedProductAmount value" + totalCalculatedProductPriceAmount);

			if (totalCalculatedProductPriceAmount != null) {

				try {

					CustomerEntity customerEntity = getCustomerDetailsById(orderDtoToEntity.getCustomerId());

					customerBalanceAmount = customerEntity.getCustomerBalance();

					log.info("customerEntity getCustomerPreviousBalance value >>>>>" + customerBalanceAmount);

					if (customerBalanceAmount != null) {
						
						  totalOrderInvoiceAmountBalance = OrderAllAmountCalcuationUtil.getCalculatedTotalInvoiceAmount(totalOrderInvoiceAmountBalance,
								totalCalculatedProductPriceAmount, customerBalanceAmount);
						  
						   orderDtoToEntity.setTotalOrderAmount(totalOrderInvoiceAmountBalance);
						   
						   log.info("totalOrderInvoiceAmountBalance value >>>>>" + totalOrderInvoiceAmountBalance);
						  
						  }
						
					}  catch (Exception e) {
					// TODO: handle exception
				}

			} else {
				log.error("Order Id is null");
				// throw exception here
			}

		} catch (EntityExistsException exception) {
			log.error("Could not save the Order" + exception.getMessage());
			// throw exception here
		}
	}

	

	private CustomerEntity getCustomerDetailsById(Long customerId) {
		CustomerEntity customerEntity;

		Optional<CustomerEntity> getCustomerBalance = customerDetailsRepository.findById(customerId);

		if (getCustomerBalance.isPresent()) {
			customerEntity = getCustomerBalance.get();
			// orderPaymentResponse =
			// OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity);
		} else {
			return null;
		}
		return customerEntity;
	}

}
