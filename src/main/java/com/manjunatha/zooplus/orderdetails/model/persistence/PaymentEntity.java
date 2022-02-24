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
@Table(name="PAYMENT")
@SequenceGenerator(name="SQ_PAYMENT_ID", sequenceName="SQ_PAYMENT_ID",allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PAYMENT_ID")
	@Column(name="ID")
	private Long id;
	
	@Column(name="CUSTOMER_ID")
	private int customerId;
	
	@Column(name="ORDER_ID")
	private int orderId;
	
	@Column(name="PAYMENT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	
	@Column(name="PAID_AMOUNT")
	private BigDecimal paidAmount;
	
	@Column(name="INVOICE_AMOUNT")
	private BigDecimal invoiceAmount;
	
	@Column(name="ORDER_BALANCE")
	private BigDecimal orderBalance;
	
	@Column(name = "PAYMENT_MODE")
	private String paymentMode;
	

	public PaymentEntity(int customerId, int orderId, Date paymentDate, BigDecimal paidAmount, BigDecimal invoiceAmount,
			BigDecimal orderBalance,String paymentMode) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.paymentDate = paymentDate;
		this.paidAmount = paidAmount;
		this.invoiceAmount = invoiceAmount;
		this.orderBalance = orderBalance;
		this.paymentMode = paymentMode;
	}
	
	

}