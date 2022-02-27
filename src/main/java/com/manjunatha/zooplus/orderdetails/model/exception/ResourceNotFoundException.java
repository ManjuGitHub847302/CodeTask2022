package com.manjunatha.zooplus.orderdetails.model.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException  extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }

}
