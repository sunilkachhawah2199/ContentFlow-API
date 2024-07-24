package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // make class controller
@RequestMapping("/api") //base url
public class CommentController {
    private final CommentService commentService; // use interface
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    // create comment
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto res=commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
//--------------------------------------------------------------------
    // fetch all comment for particular post
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getAllComments(@PathVariable(value = "postId") long postId){
        List<CommentDto> res=commentService.getAllComment(postId);
        return res;
    }

//    ----------------------------------------------------------------
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId){
        CommentDto commentDto=commentService.getCommentById(commentId, postId);
        return new ResponseEntity<>(commentDto, HttpStatus.FOUND);
    }

//    ------------------------------------------------------------
    // update comment
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId, @RequestBody CommentDto commentDto){
        CommentDto res=commentService.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // delete comment
    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletComment(@PathVariable(value = "postId") long postId,
                                               @PathVariable(value = "commentId") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.OK);
    }
}