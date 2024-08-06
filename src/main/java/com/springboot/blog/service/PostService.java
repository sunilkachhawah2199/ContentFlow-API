package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    // method to create post
    PostDto createPost(PostDto postDto);

    // mehtod to get all posts without pagination
    List<PostDto> getAllPost();

    // method to get all posy with paging

    // pageSize --> means how many data on 1 page like in 10 data if pageSize=5 then next 5 entry will be on next page
    PostResponse getAllPostPagination(int PageNo, int pageSize, String sortBy, String sortDir);

    // method to get post by id
    PostDto getPostById(Long id);

    // method to update post by id
    PostDto updatePost(Long id, PostDto postDto);

    // delete post by id
    void deletePost(Long id);
}