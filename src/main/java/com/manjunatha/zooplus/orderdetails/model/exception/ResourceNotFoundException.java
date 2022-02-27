package com.manjunatha.zooplus.orderdetails.model.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resourceName;
    private String fieldName;
    private long fieldValue;
    private String messageDescrption;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, String messageDescrption) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, messageDescrption));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.messageDescrption = messageDescrption;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }
    
    public String getMessageDescrption() {
        return messageDescrption;
    }

    public long getFieldValue() {
        return fieldValue;
    }

}
