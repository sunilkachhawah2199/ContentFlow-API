package com.springboot.blog.payload;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


// that parameter which we will return if error occurs
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

}
