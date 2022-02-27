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

/**
 * @author Manjunatha
 *
 */
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

		OrderDetailsInfoDto orderDtoToEntity = OrderPaymentServiceUtil.convertRequestToDto(orderDetailsRequest);

		String getCustomerPreviousBalance ="";

		BigDecimal totalPriceInvoiceAmount_TP = BigDecimal.ZERO;
		
		// Calculate logic for getTotalOrderAmount and getCustomerPrevious Balance Start
		getOrderTotalAmount(orderDtoToEntity,getCustomerPreviousBalance,totalPriceInvoiceAmount_TP);
		
		getCustomerPreviousBalance = getCustomerPrevBalance(orderDtoToEntity, getCustomerPreviousBalance);

		log.info("getCustomerPreviousBalance" + getCustomerPreviousBalance);
		
		// Calculate logic for getTotalOrderAmount and getCustomerPrevious Balance Start End
		
		//createOrderDetails Starts
		
		OrderDetailsEntity orderDetailsEntity = createOrderDetails(orderDtoToEntity);

		//createOrderDetails Ends
		
		return OrderPaymentServiceUtil.convertOrderDtoToResponse(
				OrderPaymentServiceUtil.convertOrderEntityToDto(orderDetailsEntity), getCustomerPreviousBalance);

	}

	
	
	@Override
	public CustomerBalanceResponse getCustomerBalance(String customerId) {

		log.info("Entering customerId getCustomerBalance method >>>" + customerId);

		CustomerEntity customerEntity = getCustomerDetailsById(Long.parseLong(customerId));

		log.info("Exit  getCustomerBalance  method >>>" + customerEntity);

		return OrderPaymentServiceUtil.convertCustomerEntityResponse(customerEntity);
	}
	
	@Override
	public OrderPaymentResponse registerPayment(OrderPaymentRequest orderPaymentRequest) {

		log.info("Entering the orderPaymentRequest method" + orderPaymentRequest.toString());
		
		OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse();

		OrderPaymentInfoDto paymentDtoToEntity = OrderPaymentServiceUtil.convertpaymentDtoEntity(orderPaymentRequest);
	
		BigDecimal totalPriceInvoiceAmount_TP = BigDecimal.ZERO;

		// Logic to  producutPriceInvoiceAmount_PP Amount logic Start
		
		log.info("Entering the totalPriceInvoiceAmount_TP before Service request" + totalPriceInvoiceAmount_TP);
		
		totalPriceInvoiceAmount_TP = getProducutPriceInvoiceAmount_PP(paymentDtoToEntity, totalPriceInvoiceAmount_TP);
		
		log.info("Exit the totalPriceInvoiceAmount_TP before Service request" + totalPriceInvoiceAmount_TP);
		
		// Logic to get producutPriceInvoiceAmount_PP Amount logic Ends
		
		//Logic to Calculate Order balance Amount Starts
		
		calculateOrderBalance(orderPaymentRequest, paymentDtoToEntity);
		
		//Logic to Calculate Order balance Amount Ends
		
		//Logic to Calculate UpdateCustomerbalance Starts
		
		 calculateAndUpdateCustomerBalance(paymentDtoToEntity, totalPriceInvoiceAmount_TP);
		 
		//Logic to Calculate UpdateCustomerbalance Ends
		
		 //Finally Perform the Payment operation Starts
		 
		 orderPaymentResponse = performOrderPayment(orderPaymentRequest, paymentDtoToEntity,orderPaymentResponse);
		   
		//Finally Perform the Payment operation Ends
			
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
	
	private String getCustomerPrevBalance(OrderDetailsInfoDto orderDtoToEntity, String getCustomerPreviousBalance) {
		if(getCustomerPreviousBalance.isEmpty() || getCustomerPreviousBalance == null) {
			CustomerEntity customerEntity = getCustomerDetailsById(orderDtoToEntity.getCustomerId());
			getCustomerPreviousBalance = customerEntity.getCustomerBalance().toString();
		}
		return getCustomerPreviousBalance;
	}

	private OrderDetailsEntity createOrderDetails(OrderDetailsInfoDto orderDtoToEntity) {
		OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity(orderDtoToEntity.getCustomerId(),
				orderDtoToEntity.getProductId(), orderDtoToEntity.getOrderDate(), orderDtoToEntity.getOrderStatus(),
				orderDtoToEntity.getComments(), orderDtoToEntity.getProductsPriceInvoiceAmount(),
				orderDtoToEntity.getTotalProductsPriceInvoiceAmount());

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
		return orderDetailsEntity;
	}

	

	private OrderPaymentResponse performOrderPayment(OrderPaymentRequest orderPaymentRequest, OrderPaymentInfoDto paymentDtoToEntity,OrderPaymentResponse orderPaymentResponse) {
		
		PaymentEntity orderPaymentEntity;
		
			Optional<PaymentEntity> OrderBalanceDetails = orderPaymentRepository.findOrderBalanceByOrderId(paymentDtoToEntity.getOrderId());

			log.info("Entering OrderPaymentResponse OrderBalanceDetails.isPresent() method >>> "
					+ OrderBalanceDetails.isPresent());
			if (!OrderBalanceDetails.isPresent()) {
				
				 orderPaymentEntity = new PaymentEntity(paymentDtoToEntity.getCustomerId(),
						paymentDtoToEntity.getOrderId(), paymentDtoToEntity.getPaymentDate(),
						paymentDtoToEntity.getPaidAmount(), paymentDtoToEntity.getProductsPriceInvoiceAmount(),
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
				return OrderPaymentServiceUtil.convertPaymentDtoToResponse(OrderPaymentServiceUtil.convertPaymentEntityToDto(orderPaymentEntity));
			} else {
				log.error("Payment Cannot be done for the same order Id");
			}
			
		return orderPaymentResponse;
	}

	private void calculateAndUpdateCustomerBalance(OrderPaymentInfoDto paymentDtoToEntity,
			BigDecimal totalPriceInvoiceAmount_TP) {
		if(paymentDtoToEntity.getCustomerId()!=null && totalPriceInvoiceAmount_TP !=null) {
			 
			 BigDecimal customerBalance_CB = BigDecimal.ZERO;
			 BigDecimal paidAmount_PA = paymentDtoToEntity.getPaidAmount();
			 
			 CustomerEntity customerEntity;
			 
				try {
					Optional<CustomerEntity> getCustomerBalanceEntity = customerDetailsRepository.findById(paymentDtoToEntity.getCustomerId());
					log.info("Entering getCustomerBalanceEntity getCustomerBalanceEntity >>> " + getCustomerBalanceEntity.toString() + "getCustomerBalanceEntity" + getCustomerBalanceEntity);
					if (getCustomerBalanceEntity.isPresent()) {
						customerEntity = getCustomerBalanceEntity.get();
						customerBalance_CB = customerEntity.getCustomerBalance();
						
						log.info("customerBalance_CB Before>>>" + customerBalance_CB + " "+ paidAmount_PA  + " " + totalPriceInvoiceAmount_TP);
						customerBalance_CB =  OrderAllAmountCalcuationUtil.getUpdatedCustomerBalance(customerBalance_CB,paidAmount_PA,totalPriceInvoiceAmount_TP);
						log.info("getUpdatedCustomerBalance customerBalance_CB<<<<" + customerBalance_CB + " "+paidAmount_PA  + " " + totalPriceInvoiceAmount_TP);
					 
						try {
							 if(customerBalance_CB!=null) {
								 log.info("getUpdatedCustomerBalance Before>>>" +customerBalance_CB + "getCustomerBalanceEntity" +getCustomerBalanceEntity);
								 customerEntity.setCustomerBalance(customerBalance_CB);
								 customerDetailsRepository.save(customerEntity);
								 log.info("getUpdatedCustomerBalance Exit>>>" +customerBalance_CB);
							 }
							
							} catch (Exception e) {
								// TODO: handle exception
							}
						
					} else {
						//log the error 
					}

				} catch (Exception e) {
					
				}
				
		 }
	}

	private void calculateOrderBalance(OrderPaymentRequest orderPaymentRequest,
			OrderPaymentInfoDto paymentDtoToEntity) {
		if(orderPaymentRequest.getPaidAmount()!=null && paymentDtoToEntity.getProductsPriceInvoiceAmount()!=null) {
			
			log.info("Entering Order Balance Logic orderPaymentRequest.getPaidAmount() "+orderPaymentRequest.getPaidAmount() 
			+"paymentDtoToEntity.getProductsPriceInvoiceAmount()" + paymentDtoToEntity.getProductsPriceInvoiceAmount());
			
			BigDecimal getorderBalance_OB = OrderAllAmountCalcuationUtil.getCalculatedOrderBalance(paymentDtoToEntity);
			
			log.info("getorderBalance_OB Order Balance getorderBalance_OB>>> "+ getorderBalance_OB);
			
			if(getorderBalance_OB!=null) {
				paymentDtoToEntity.setOrderBalance(getorderBalance_OB);
			}
		}
	}

	private BigDecimal getProducutPriceInvoiceAmount_PP(OrderPaymentInfoDto paymentDtoToEntity,
			BigDecimal totalPriceInvoiceAmount_TP) {
		OrderDetailsEntity orderDetailsEntity;
		try {

			Optional<OrderDetailsEntity> orderInvoiceAmountEntity = orderDetailsRepository.findById(paymentDtoToEntity.getOrderId());

			log.info("Entering producutPriceInvoiceAmount_PP producutPriceInvoiceAmount_PP.isPresent() producutPriceInvoiceAmount_PP >>> " + orderInvoiceAmountEntity.isPresent());
			if (orderInvoiceAmountEntity.isPresent()) {
				orderDetailsEntity = orderInvoiceAmountEntity.get();
				paymentDtoToEntity.setProductsPriceInvoiceAmount(orderDetailsEntity.getProductsPriceInvoiceAmount());
				totalPriceInvoiceAmount_TP = orderDetailsEntity.getTotalProductsPriceInvoiceAmount();
				
			} else {
				//throw the error here
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return totalPriceInvoiceAmount_TP;
	}


	private void getOrderTotalAmount(OrderDetailsInfoDto orderDtoToEntity, String getCustomerPreviousBalance,BigDecimal totalPriceInvoiceAmount_TP) {

		 List<String> productIdList = Stream.of(orderDtoToEntity.getProductId().split(",")).collect(Collectors.toList());

		  BigDecimal producutPriceInvoiceAmount_PP; 
		  BigDecimal customerBalanceAmount_CB ;

		try {

			List<ProductEntity> productPriceListDetails = productRepository.findtheProductPriceByProductId(productIdList);

			List<BigDecimal> productPriceList = productPriceListDetails.stream().map(ProductEntity::getProductPrice)
					.collect(Collectors.toList());

			producutPriceInvoiceAmount_PP = productPriceList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

			log.info("producutPriceInvoiceAmount_PP value" + producutPriceInvoiceAmount_PP);

			if (producutPriceInvoiceAmount_PP != null) {
				
				orderDtoToEntity.setProductsPriceInvoiceAmount(producutPriceInvoiceAmount_PP);

				try {

					CustomerEntity customerEntity = getCustomerDetailsById(orderDtoToEntity.getCustomerId());

					customerBalanceAmount_CB = customerEntity.getCustomerBalance();

					log.info("customerEntity customerBalanceAmount_CB value >>>>>" + customerBalanceAmount_CB);

					if (customerBalanceAmount_CB != null) {
						
						totalPriceInvoiceAmount_TP = OrderAllAmountCalcuationUtil.getCalculatedTotalInvoiceAmount(totalPriceInvoiceAmount_TP,producutPriceInvoiceAmount_PP,
								customerBalanceAmount_CB);
						  
						   orderDtoToEntity.setTotalProductsPriceInvoiceAmount(totalPriceInvoiceAmount_TP);
						   
						   log.info("setTotalProductsPriceInvoiceAmount value >>>>>" + totalPriceInvoiceAmount_TP);
						  
						  }
					
					}  catch (Exception e) {
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
