package com.manjunatha.zooplus.orderdetails.model.dto.info;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApiErrorInfoDto {
	
    private Date timestamp;
	private HttpStatus status;
	private String message;
	private List<String> errors;

}
