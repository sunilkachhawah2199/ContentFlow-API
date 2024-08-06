package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;


public interface CommentService {
    // method-->  create comment for post
    CommentDto createComment(long postId, CommentDto commentDto);

    // method --> fetch all comment for particular post
    List<CommentDto> getAllComment(long postId);

    // method--> get comment by id for particular post
    CommentDto getCommentById(long commentId, long postId);

    //method --> update comment by id
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    // delete comment for post
    void deleteComment(long postId, long commentId);
}