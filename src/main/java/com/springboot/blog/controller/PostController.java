package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
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
    public ResponseEntity<PostDto> create(@Valid @RequestBody PostDto postDto){
        // method of postService which return PostDto
        PostDto res=postService.createPost(postDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    // get all post without paging and sorting
    // controller to get all posts
    // if we don't specify status code then it will return status code 200
    @GetMapping("/")
    public List<PostDto> getAll(){
        return postService.getAllPost();
    }


    // get all post with paging and sorting
    // @RequestParam --> is used to extract query parameter from request | required=false --> means optional
    @GetMapping("/paging")
    public PostResponse getAllWithPagination(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                             @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                             @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir){
        return postService.getAllPostPagination(pageNo, pageSize, sortBy, sortDir);
    }


    // get post by id
    /*we use @PathVariable to map path parameter to method parameter */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        PostDto res=postService.getPostById(id);
        return new ResponseEntity<>(res, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") long id,@Valid @RequestBody PostDto postDto){
        PostDto res=postService.updatePost(id, postDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post Entity deleted Successfully", HttpStatus.OK);
    }
}