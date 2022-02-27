package com.manjunatha.zooplus.orderdetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manjunatha.zooplus.orderdetails.model.persistence.ProductEntity;


@Repository
@Transactional(readOnly = true) 
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	
	@Query(nativeQuery =true,value = "SELECT * FROM PRODUCT as e WHERE e.Id IN (:productId)")  
	List<ProductEntity> findtheProductPriceByProductId(@Param("productId") List<String> productId); 

}
