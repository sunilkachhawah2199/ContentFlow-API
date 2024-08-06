package com.springboot.blog.exception;

//when we are trying to find comment for particular post if comment is not present for that post then throw exception
// we will throw this exception when comment not belongs to particular post

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
public class BlogAPIException extends RuntimeException{
    // parameter of exception
    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}