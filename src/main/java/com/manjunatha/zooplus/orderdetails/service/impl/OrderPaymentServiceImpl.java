package com.manjunatha.zooplus.orderdetails.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manjunatha.zooplus.orderdetails.model.dto.info.OrderDetailsInfoDto;
import com.manjunatha.zooplus.orderdetails.model.dto.info.OrderPaymentInfoDto;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderDetailsRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.request.OrderPaymentRequest;
import com.manjunatha.zooplus.orderdetails.model.dto.response.CustomerBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderBalanceResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderDetailsResponse;
import com.manjunatha.zooplus.orderdetails.model.dto.response.OrderPaymentResponse;
import com.manjunatha.zooplus.orderdetails.model.exception.HandleApiErrorException;
import com.manjunatha.zooplus.orderdetails.model.exception.ResourceNotFoundException;
import com.manjunatha.zooplus.orderdetails.model.persistence.CustomerEntity;
import com.manjunatha.zooplus.orderdetails.model.persistence.OrderDetailsEntity;
import com.manjunatha.zooplus.orderdetails.model.persistence.PaymentEntity;
import com.manjunatha.zooplus.orderdetails.model.persistence.ProductEntity;
import com.manjunatha.zooplus.orderdetails.repository.CustomerRepository;
import com.manjunatha.zooplus.orderdetails.repository.OrderDetailsRepository;
import com.manjunatha.zooplus.orderdetails.repository.OrderPaymentRepository;
import com.manjunatha.zooplus.orderdetails.repository.ProductRepository;
import com.manjunatha.zooplus.orderdetails.service.OrderPaymentService;
import com.manjunatha.zooplus.orderdetails.service.util.OrderAllAmountCalcuationUtil;
import com.manjunatha.zooplus.orderdetails.service.util.OrderPaymentServiceUtil;

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
	@Transactional(rollbackFor= {Exception.class,RuntimeException.class})
	public OrderDetailsResponse registerOrder(OrderDetailsRequest orderDetailsRequest) {

		log.info("Entering the OrderDetailsResponse method" + orderDetailsRequest.toString());
		
		validateRequestOrderDetails(orderDetailsRequest.getCustomerId());
		
		OrderDetailsInfoDto orderResponseObjectDetails = OrderPaymentServiceUtil.convertRequestToDto(orderDetailsRequest);

		String getCustomerPreviousBalance ="";

		BigDecimal totalPriceInvoiceAmount_TP = BigDecimal.ZERO;
		
		// Calculate logic for getTotalOrderAmount and getCustomerPrevious Balance Start
		getOrderTotalAmount(orderResponseObjectDetails,getCustomerPreviousBalance,totalPriceInvoiceAmount_TP);
		
		getCustomerPreviousBalance = getCustomerPrevBalance(orderResponseObjectDetails, getCustomerPreviousBalance);

		log.info("getCustomerPreviousBalance" + getCustomerPreviousBalance);
		
		// Calculate logic for getTotalOrderAmount and getCustomerPrevious Balance Start End
		
		//createOrderDetails Starts
		
		OrderDetailsEntity orderDetailsEntity = createOrderDetails(orderResponseObjectDetails);

		//createOrderDetails Ends
		
		return OrderPaymentServiceUtil.convertOrderDtoToResponse(
				OrderPaymentServiceUtil.convertOrderEntityToDto(orderDetailsEntity), getCustomerPreviousBalance);

	}

	
	@Override
	@Transactional(rollbackFor= {Exception.class,RuntimeException.class})
	public OrderPaymentResponse registerPayment(OrderPaymentRequest orderPaymentRequest) {

		log.info("Entering the orderPaymentRequest method");
		
		orderPaymentRequest = OrderPaymentServiceUtil.validatePaymentRequestParmater(orderPaymentRequest);
		
		orderPaymentRequest = validateOrderPaymentRequest(orderPaymentRequest);
		
		OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse();

		OrderPaymentInfoDto paymentDtoToEntity = OrderPaymentServiceUtil.convertpaymentDtoEntity(orderPaymentRequest);
		
		BigDecimal totalPriceInvoiceAmount_TP = BigDecimal.ZERO;

		totalPriceInvoiceAmount_TP = getProducutPriceInvoiceAmount_PP(paymentDtoToEntity, totalPriceInvoiceAmount_TP);
		
		calculateOrderBalance(orderPaymentRequest, paymentDtoToEntity);
		
		calculateAndUpdateCustomerBalance(paymentDtoToEntity, totalPriceInvoiceAmount_TP);
		
		CustomerEntity customerEntity = getCustomerDetailsById(paymentDtoToEntity.getCustomerId());
		 
		String getUpdatedCustomerBalance = customerEntity.getCustomerBalance().toString();

		orderPaymentResponse = performOrderPayment(orderPaymentRequest, paymentDtoToEntity,orderPaymentResponse,getUpdatedCustomerBalance);
		   
		log.info("Exit the orderPaymentRequest method");	
		
		return orderPaymentResponse;
		 
	}
	
	
	@Override
	public CustomerBalanceResponse getCustomerBalance(Long customerId) {

		log.info("Entering customerId getCustomerBalance method >>>" + customerId);

		CustomerEntity customerEntity = getCustomerDetailsById(customerId);

		log.info("Exit  getCustomerBalance  method >>>" + customerEntity);

		return OrderPaymentServiceUtil.convertCustomerEntityResponse(customerEntity);
	}
	
	
	
	@Override
	public OrderBalanceResponse getOrderBalance(Long orderId) {

		log.info("Entering OrderPaymentResponse getOrderBalance method >>>");

		PaymentEntity paymentEntity;

		Optional<PaymentEntity> OrderBalanceDetails = orderPaymentRepository
				.findOrderBalanceByOrderId(orderId);

		if (OrderBalanceDetails.isPresent()) {
		     paymentEntity = OrderBalanceDetails.get();
		} else {
			throw new ResourceNotFoundException("Order Id not found ");
		}

		log.info("Exit Response method >>> " + OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity));

		return OrderPaymentServiceUtil.convertPaymentEntityResponse(paymentEntity);

	}
	
	private OrderPaymentRequest validateOrderPaymentRequest(OrderPaymentRequest orderPaymentRequest) {
			
		log.info("Entering validateOrderPaymentRequest");
			
		 OrderDetailsEntity orderDetailsEntityLatest;
		
		Optional<OrderDetailsEntity> orderValidationCheckInfo = orderDetailsRepository. findById(orderPaymentRequest.getOrderId());
		
		if (orderValidationCheckInfo.isPresent()) {
				orderDetailsEntityLatest = orderValidationCheckInfo.get();
				 if(orderDetailsEntityLatest.getCustomerId().compareTo(orderPaymentRequest.getCustomerId()) > 0 || orderDetailsEntityLatest.getCustomerId().compareTo(orderPaymentRequest.getCustomerId()) < 0) {
					 throw new ResourceNotFoundException("Please send the Customer Id Which have same Order id");
			}
		}
		
		log.info("Exit validateOrderPaymentRequest");
			
		return orderPaymentRequest;
		}
	
	private String getCustomerPrevBalance(OrderDetailsInfoDto orderDtoToEntity, String getCustomerPreviousBalance) {
		
		if(getCustomerPreviousBalance.isEmpty() || getCustomerPreviousBalance == null) {
			CustomerEntity customerEntity = getCustomerDetailsById(orderDtoToEntity.getCustomerId());
			getCustomerPreviousBalance = customerEntity.getCustomerBalance().toString();
		}
		return getCustomerPreviousBalance;
	}
	

	private OrderDetailsEntity createOrderDetails(OrderDetailsInfoDto orderDtoToEntity) {
		
		log.info("Entering the orderDetailsEntity before Service orderDetailsEntity >>" + orderDtoToEntity.toString());
		
		OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity(orderDtoToEntity.getCustomerId(),
				orderDtoToEntity.getProductId(), orderDtoToEntity.getOrderDate(), orderDtoToEntity.getOrderStatus(),
				orderDtoToEntity.getComments(), orderDtoToEntity.getProductsPriceInvoiceAmount(),
				orderDtoToEntity.getTotalProductsPriceInvoiceAmount());

		try {
			
			orderDetailsRepository.save(orderDetailsEntity);
			
		} catch (EntityExistsException exception) {
			log.error("Exception Message"+ exception.getMessage());
		}

		if (orderDetailsEntity == null || orderDetailsEntity.getId() == null
				|| orderDetailsEntity.getId().toString().isEmpty()) {
			log.error("Order Id is null");
			throw new ResourceNotFoundException("Order ID not found");
		}

		log.info("orderDetailsEntity.getId() >>>>" + orderDetailsEntity.getId());
		orderDetailsEntity.setId(orderDetailsEntity.getId());
		return orderDetailsEntity;
	}

	

	private OrderPaymentResponse performOrderPayment(OrderPaymentRequest orderPaymentRequest, OrderPaymentInfoDto paymentDtoToEntity,OrderPaymentResponse orderPaymentResponse,String getCustomerUpdatedBalance) {
		
		log.info("Entering OrderPaymentResponse OrderBalanceDetails.isPresent() method >>> ");
		
		PaymentEntity orderPaymentEntity;
		
		Optional<PaymentEntity> OrderBalanceDetails = orderPaymentRepository.findOrderBalanceByOrderId(paymentDtoToEntity.getOrderId());

		if (!OrderBalanceDetails.isPresent()) {
			
			 orderPaymentEntity = new PaymentEntity(paymentDtoToEntity.getCustomerId(),
					paymentDtoToEntity.getOrderId(), paymentDtoToEntity.getPaymentDate(),
					paymentDtoToEntity.getPaidAmount(), paymentDtoToEntity.getProductsPriceInvoiceAmount(),
					paymentDtoToEntity.getOrderBalance(), orderPaymentRequest.getPaymentMode());

			try {
				orderPaymentRepository.save(orderPaymentEntity);
			} catch (EntityExistsException exception) {
				log.error("Could not save the Order" + exception.getMessage());
			}
			if (orderPaymentEntity == null || orderPaymentEntity.getId() == null
					|| orderPaymentEntity.getId().toString().isEmpty()) {
				throw new ResourceNotFoundException("Order Id not found");
			}
			orderPaymentEntity.setId(orderPaymentEntity.getId());
			
			return OrderPaymentServiceUtil.convertPaymentDtoToResponse
					(OrderPaymentServiceUtil.convertPaymentEntityToDto(orderPaymentEntity),getCustomerUpdatedBalance);
		}  else {
			log.error("Payment Cannot be done for the same order Id");
			throw new HandleApiErrorException(HttpStatus.BAD_REQUEST, "Payment Cannot be done for the same order Id");
		}
			
	}

	private void calculateAndUpdateCustomerBalance(OrderPaymentInfoDto paymentDtoToEntity,BigDecimal totalPriceInvoiceAmount_TP) {
		
		if(paymentDtoToEntity.getCustomerId()!=null && totalPriceInvoiceAmount_TP !=null) {
			 
			 BigDecimal customerBalance_CB = BigDecimal.ZERO;
			 BigDecimal paidAmount_PA = paymentDtoToEntity.getPaidAmount();
			 
			 CustomerEntity customerEntity;
			 
		Optional<CustomerEntity> getCustomerBalanceEntity = customerDetailsRepository.findById(paymentDtoToEntity.getCustomerId());
		
		if (getCustomerBalanceEntity.isPresent()) {
			customerEntity = getCustomerBalanceEntity.get();
			customerBalance_CB = customerEntity.getCustomerBalance();
			
			customerBalance_CB =  OrderAllAmountCalcuationUtil.getUpdatedCustomerBalance(customerBalance_CB,paidAmount_PA,totalPriceInvoiceAmount_TP);
				 if(customerBalance_CB!=null) {
					 customerEntity.setCustomerBalance(customerBalance_CB);
					 customerDetailsRepository.save(customerEntity);
				 }
			
		} else {
			throw new ResourceNotFoundException("customerId Not found");
		}
  }
	}

	private void calculateOrderBalance(OrderPaymentRequest orderPaymentRequest,
			OrderPaymentInfoDto paymentDtoToEntity) {
		if(orderPaymentRequest.getPaidAmount()!=null && paymentDtoToEntity.getProductsPriceInvoiceAmount()!=null) {
			
			BigDecimal getorderBalance_OB = OrderAllAmountCalcuationUtil.getCalculatedOrderBalance(paymentDtoToEntity);
			
			if(getorderBalance_OB!=null) {
				paymentDtoToEntity.setOrderBalance(getorderBalance_OB);
			}
		}
	}

	private BigDecimal getProducutPriceInvoiceAmount_PP(OrderPaymentInfoDto paymentDtoToEntity,
			BigDecimal totalPriceInvoiceAmount_TP) {
		
		OrderDetailsEntity orderDetailsEntity;

		Optional<OrderDetailsEntity> orderInvoiceAmountEntity = orderDetailsRepository.findById(paymentDtoToEntity.getOrderId());
		if (orderInvoiceAmountEntity.isPresent()) {
			orderDetailsEntity = orderInvoiceAmountEntity.get();
			paymentDtoToEntity.setProductsPriceInvoiceAmount(orderDetailsEntity.getProductsPriceInvoiceAmount());
			totalPriceInvoiceAmount_TP = orderDetailsEntity.getTotalProductsPriceInvoiceAmount();
		} else {
			throw new ResourceNotFoundException("Order Id not Present");
		}

		return totalPriceInvoiceAmount_TP;
	}


	private void getOrderTotalAmount(OrderDetailsInfoDto orderDtoToEntity, String getCustomerPreviousBalance,BigDecimal totalPriceInvoiceAmount_TP) {

		 List<String> productIdList = Stream.of(orderDtoToEntity.getProductId().split(",")).collect(Collectors.toList());

		  BigDecimal producutPriceInvoiceAmount_PP; 
		  BigDecimal customerBalanceAmount_CB ;

		List<ProductEntity> productPriceListDetails = productRepository.findtheProductPriceByProductId(productIdList);
		
		List<BigDecimal> productPriceList = productPriceListDetails.stream().map(ProductEntity::getProductPrice)
				.collect(Collectors.toList());
		
		if(productPriceList.size() <= 0) {
			throw new ResourceNotFoundException("Product Details are Empty");
		}

		producutPriceInvoiceAmount_PP = productPriceList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

		orderDtoToEntity.setProductsPriceInvoiceAmount(producutPriceInvoiceAmount_PP);

		CustomerEntity customerEntity = getCustomerDetailsById(orderDtoToEntity.getCustomerId());

		customerBalanceAmount_CB = customerEntity.getCustomerBalance();

		totalPriceInvoiceAmount_TP = OrderAllAmountCalcuationUtil.getCalculatedTotalInvoiceAmount(totalPriceInvoiceAmount_TP,producutPriceInvoiceAmount_PP,
							customerBalanceAmount_CB);
					  
		orderDtoToEntity.setTotalProductsPriceInvoiceAmount(totalPriceInvoiceAmount_TP);
					   
	}

	private CustomerEntity getCustomerDetailsById(Long customerId) {
		CustomerEntity customerEntity;

		Optional<CustomerEntity> getCustomerBalance = customerDetailsRepository.findById(customerId);

		if (getCustomerBalance.isPresent()) {
			customerEntity = getCustomerBalance.get();
		} else {
			throw new ResourceNotFoundException("Customer Id not Present");
		}
		return customerEntity;
	}

	private void validateRequestOrderDetails(Long orderDetailsRequestCustomerId) {
		
		if(orderDetailsRequestCustomerId == null) {
			throw new HandleApiErrorException(HttpStatus.BAD_REQUEST, "Customer Id cannot be Empty");
		}
		
		if(orderDetailsRequestCustomerId != null && orderDetailsRequestCustomerId < 1) {
			throw new HandleApiErrorException(HttpStatus.BAD_REQUEST, "Customer Id cannot be Negative or 0");
		}
	}
}
