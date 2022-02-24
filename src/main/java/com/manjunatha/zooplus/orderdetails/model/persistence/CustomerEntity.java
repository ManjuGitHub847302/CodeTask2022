package com.manjunatha.zooplus.orderdetails.model.persistence;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="CUSTOMER")
@SequenceGenerator(name="SQ_CUSTOMER_ID", sequenceName="SQ_CUSTOMER_ID",allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CUSTOMER_ID")
	@Column(name="ID")
	private Long id;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="POST_CODE")
	private String postCode;
	
	@Column(name="CUSTOMER_BALANCE")
	private BigDecimal customerBalance;
	
	
	
		
}
