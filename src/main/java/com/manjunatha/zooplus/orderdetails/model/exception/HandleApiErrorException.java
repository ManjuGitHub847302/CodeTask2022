package com.manjunatha.zooplus.orderdetails.model.exception;

import org.springframework.http.HttpStatus;

public class HandleApiErrorException extends RuntimeException {
	
		 	
		  private static final long serialVersionUID = -6157943907225863190L;
		  
		  private HttpStatus status; 
		  private String message;
		  
		  public HandleApiErrorException(HttpStatus status, String message) 
		  {
			  this.status = status; 
		      this.message = message; 
		  }
		  
		  public HandleApiErrorException(String message, HttpStatus status, String message1) 
		  { 
			  super(message); 
			  this.status = status; 
			  this.message = message1; 
		   }
		  
		  public HttpStatus getStatus() { return status; }
		  
		  @Override 
		  public String getMessage() 
		  { 
			  return message; 
			  }
					 
}
