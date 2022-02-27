package com.manjunatha.zooplus.orderdetails.model.exception;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.manjunatha.zooplus.orderdetails.model.dto.info.ErrorDetailsInfoDto;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	// handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsInfoDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
    	ErrorDetailsInfoDto errorDetails = new ErrorDetailsInfoDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

	
	  @ExceptionHandler(HandleApiErrorException.class) 
	  public
	  ResponseEntity<ErrorDetailsInfoDto> handleApiErrorException(HandleApiErrorException exception, WebRequest webRequest)
	  { 
		  ErrorDetailsInfoDto errorDetails = new ErrorDetailsInfoDto(new Date(), exception.getMessage(),webRequest.getDescription(false)); 
		  return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST); }
	 
    
    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsInfoDto> handleGlobalException(Exception exception,
                                                               WebRequest webRequest){
    	ErrorDetailsInfoDto errorDetails = new ErrorDetailsInfoDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatus status,WebRequest request) 
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

}