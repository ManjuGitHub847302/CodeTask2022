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
@Table(name="PRODUCT")
@SequenceGenerator(name="SQ_PRODUCT_ID", sequenceName="SQ_PRODUCT_ID",allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUCT_ID")
	@Column(name="ID")
	private Long id;
	
	@Column(name="PRODUCT_NAME")
	private String productName;
	
	@Column(name="PRODUCT_DESCRIPTION")
	private String productDescription;
	
	@Column(name="PRODUCT_PRICE")
	private BigDecimal productPrice;
	

}
