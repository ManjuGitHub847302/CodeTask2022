package com.manjunatha.zooplus.orderdetails.model.dto.info;

import java.util.Date;

public class ErrorDetailsInfoDto {
	
	private Date timestamp;
    private String message;
    private String details;

    public ErrorDetailsInfoDto(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}
