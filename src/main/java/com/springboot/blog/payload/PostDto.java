package com.springboot.blog.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

//we will add that data which  need to return to client and get from client
@Data // no need get, set
public class PostDto {


    private long id;
    @NotEmpty
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    @NotEmpty
    @Size(min = 10, message = "Content must be at least 10dsad characters long")
    private String content;

    private Set<CommentDto> comments;
}