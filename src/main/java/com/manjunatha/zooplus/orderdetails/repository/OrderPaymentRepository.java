package com.manjunatha.zooplus.orderdetails.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manjunatha.zooplus.orderdetails.model.persistence.PaymentEntity;

@Repository
@Transactional(readOnly = true) 
public interface OrderPaymentRepository extends JpaRepository<PaymentEntity, Long>{
	
	@Query("SELECT m from PaymentEntity m where m.orderId = ?1")
	Optional<PaymentEntity> findOrderBalanceByOrderId(Long orderId); 

}
