package com.springboot.blog.payload;

import lombok.Data;

//we will add that data which  need to return to client and get from client
@Data // no need get, set
public class PostDto {
//    private long id; --> no need for client | it is primary key
    private long id;
    private String title;
    private String description;
    private String content;
}