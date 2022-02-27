package com.manjunatha.zooplus.orderdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manjunatha.zooplus.orderdetails.model.persistence.OrderDetailsEntity;

@Repository
@Transactional(readOnly = true) 
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {
	
}
