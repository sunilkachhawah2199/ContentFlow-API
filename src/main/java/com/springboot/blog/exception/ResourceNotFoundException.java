package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// return exception when resource not found
// we extend from RuntimeException --> for custom exception


//response status  annotation cause spring boot to respond w ith the specified http status code whenever this exception thrown from computer
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
//    parameter of exception message
    private String resourceName;
    private String fieldName;
    private String fieldValue;

//    all arg constructor
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        // message in super class
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue)); //post not found with id: '1'
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

    public String getFieldValue() {
        return fieldValue;
    }
}