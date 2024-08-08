package com.springboot.blog.payload;
import java.util.Date;

import lombok.*;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;


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
