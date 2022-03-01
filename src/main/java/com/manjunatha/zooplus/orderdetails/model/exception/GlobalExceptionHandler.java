package com.manjunatha.zooplus.orderdetails.model.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.manjunatha.zooplus.orderdetails.model.dto.info.ApiErrorInfoDto;
import com.manjunatha.zooplus.orderdetails.model.dto.response.ApiErrorResponseBuilder;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	// handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
		@Override
	    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
	            HttpMediaTypeNotSupportedException ex,
	            HttpHeaders headers,
	            HttpStatus status,
	            WebRequest request) {
			
			List<String> details = new ArrayList<String>();
			
	        StringBuilder builder = new StringBuilder();
	        builder.append(ex.getContentType());
	        builder.append(" media type is not supported. Supported media types are ");
	        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
	        
	        details.add(builder.toString());

	        ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(), HttpStatus.BAD_REQUEST, "Invalid JSON" ,details);
			
			return ApiErrorResponseBuilder.build(err);
		
		}
		
		// handleHttpMessageNotReadable : triggers when the JSON is malformed
		@Override
	    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        
			List<String> details = new ArrayList<String>();
	        details.add(ex.getMessage());
	        
	        ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),HttpStatus.BAD_REQUEST, "Malformed JSON request" ,details);
			
			return ApiErrorResponseBuilder.build(err);
	    }
		
		// handleMethodArgumentNotValid : triggers when @Valid fails
		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {
			
			List<String> details = new ArrayList<String>();
			details = ex.getBindingResult()
					    .getFieldErrors()
						.stream()
						.map(error -> error.getObjectName()+ " : " +error.getDefaultMessage())
						.collect(Collectors.toList());
			
			ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),
					HttpStatus.BAD_REQUEST,
					"Validation Errors" ,
					details);
			
			return ApiErrorResponseBuilder.build(err);
		}
		
		// handleMissingServletRequestParameter : triggers when there are missing parameters
		@Override
	    protected ResponseEntity<Object> handleMissingServletRequestParameter(
	            MissingServletRequestParameterException ex, HttpHeaders headers,
	            HttpStatus status, WebRequest request) {
			
			List<String> details = new ArrayList<String>();
			details.add(ex.getParameterName() + " parameter is missing");

			ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),HttpStatus.BAD_REQUEST, "Missing Parameters" ,details);
			
			return ApiErrorResponseBuilder.build(err);
	    }
		
		// handleMethodArgumentTypeMismatch : triggers when a parameter's type does not match
		@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
	                                                                      WebRequest request) {
	        List<String> details = new ArrayList<String>();
			details.add(ex.getMessage());
	      
			ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),HttpStatus.BAD_REQUEST, "Mismatch Type" ,details);
			
			return ApiErrorResponseBuilder.build(err);
	    }
		
		// handleConstraintViolationException : triggers when @Validated fails
		@ExceptionHandler(ConstraintViolationException.class)
		public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {
			
			List<String> details = new ArrayList<String>();
			details.add(ex.getMessage());
			
			ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),HttpStatus.BAD_REQUEST, "Constraint Violation" ,details);
			
			return ApiErrorResponseBuilder.build(err);
		}
		
		// handleResourceNotFoundException : triggers when there is not resource with the specified ID in BDD
		@ExceptionHandler(ResourceNotFoundException.class)
		public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
	       
			List<String> details = new ArrayList<String>();
			details.add(ex.getMessage());
			
			ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),HttpStatus.NOT_FOUND, "Resource Not Found" ,details);
			
			return ApiErrorResponseBuilder.build(err);
		}
		
		// handleNoHandlerFoundException : triggers when the handler method is invalid
		@Override
	    protected ResponseEntity<Object> handleNoHandlerFoundException(
	            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

	        List<String> details = new ArrayList<String>();
			details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
			
			ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),HttpStatus.BAD_REQUEST, "Method Not Found" ,details);
			
	        return ApiErrorResponseBuilder.build(err);
	        
	    }
		
		@ExceptionHandler({ Exception.class })
		public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
			
			List<String> details = new ArrayList<String>();
			details.add(ex.getLocalizedMessage());
			
			ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),HttpStatus.BAD_REQUEST, "Error occurred" ,details);
			
			return ApiErrorResponseBuilder.build(err);
		
		}
		
		 @ExceptionHandler(HandleApiErrorException.class) 
		 public ResponseEntity<Object> handleApiErrorException(HandleApiErrorException exception, WebRequest webRequest) { 
			 
			 List<String> details = new ArrayList<String>();
			 details.add(exception.getLocalizedMessage());
				
			 ApiErrorInfoDto err = new ApiErrorInfoDto(new Date(),HttpStatus.BAD_REQUEST, "Error occurred" ,details);
			 
			 return ApiErrorResponseBuilder.build(err);
		  
		  }
    

   
}
