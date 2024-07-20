package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    // method to create post
    PostDto createPost(PostDto postDto);

    // mehtod to get all posts
    List<PostDto> getAllPost();
}