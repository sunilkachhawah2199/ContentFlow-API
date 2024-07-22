package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;


public interface CommentService {
    // method-->  create comment for post
    CommentDto createComment(long postId, CommentDto commentDto);

    // method --> fetch all comment for particular post
    List<CommentDto> getAllComment(long postId);
}