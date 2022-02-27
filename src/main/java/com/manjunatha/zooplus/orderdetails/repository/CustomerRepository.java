package com.manjunatha.zooplus.orderdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manjunatha.zooplus.orderdetails.model.persistence.CustomerEntity;

@Repository
@Transactional(readOnly = true) 
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
