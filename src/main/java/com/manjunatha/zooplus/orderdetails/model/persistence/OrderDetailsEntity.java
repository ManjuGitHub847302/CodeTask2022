package com.manjunatha.zooplus.orderdetails.model.persistence;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDER_DETAILS")
@SequenceGenerator(name="SQ_ORDERINVOICE_ID", sequenceName="SQ_ORDERINVOICE_ID",allocationSize = 1)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class OrderDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ORDERINVOICE_ID")
	@Column(name = "ID")
	private Long id;

	@Column(name = "CUSTOMER_ID")
	private Long customerId;
	
	@Column(name = "PRODUCT_ID")
	private String productId;

	@Column(name = "ORDER_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@Column(name = "ORDER_STATUS")
	private int orderStatus;
	
	@Column(name = "COMMENTS")
	private String comments;

	@Column(name="TOTAL_ORDER_AMOUNT")
	private BigDecimal totalOrderAmount;

	public OrderDetailsEntity(Long customerId, String productId, Date orderDate, int orderStatus, String comments, 
			BigDecimal totalOrderAmount) {
		this.customerId = customerId;
		this.productId = productId;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.comments = comments;
		this.totalOrderAmount = totalOrderAmount;
	}
	
	
	

}
