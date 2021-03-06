package com.manjunatha.zooplus.orderdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("com.manjunatha.zooplus.orderdetails")
@EnableJpaRepositories("com.manjunatha.zooplus.orderdetails")
public class OrderPaymentDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderPaymentDetailsApplication.class, args);
	}

}
