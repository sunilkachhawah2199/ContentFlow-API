package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts") // to store base url of api

// post controller
public class PostController {


    // we will inject post service interface not exact class to make it loose coupling
    private PostService postService; // interface not class
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create post api
    @PostMapping("/")
    // response entity is generic type
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto){
        // method of postService which return PostDto
        PostDto res=postService.createPost(postDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // controller to get all posts
    // if we don't specify status code then it will return status code 200
    @GetMapping
    public List<PostDto> getAll(){
        return postService.getAllPost();
    }
}