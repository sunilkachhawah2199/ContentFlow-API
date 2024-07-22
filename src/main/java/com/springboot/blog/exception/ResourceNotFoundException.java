package com.springboot.blog.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// return exception when resource not found
// we extend from RuntimeException --> for custom exception


//response status  annotation cause spring boot to respond with the specified http status code whenever this exception thrown from computer

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
//    parameter of exception message
    private String resourceName;
    private String fieldName;
    private Long fieldValue;

//    all arg constructor
    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        // message in super class
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue)); //post not found with id: '1'
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}