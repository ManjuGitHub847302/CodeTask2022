package com.manjunatha.zooplus.orderdetails.model.dto.response;

import org.springframework.http.ResponseEntity;

import com.manjunatha.zooplus.orderdetails.model.dto.info.ApiErrorInfoDto;

public class ApiErrorResponseBuilder {

	public static ResponseEntity<Object> build(ApiErrorInfoDto apiError) {
	      return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
