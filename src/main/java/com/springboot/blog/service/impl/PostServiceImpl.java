package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {
// we save data into db repository which extends jpa repo. have all queries related method in this so we use this is service layer
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    // method to create post which take psotDto as a input
    @Override
    public PostDto createPost(PostDto postDto) {
        // convert dto into entity
        Post post=new Post();
        post=mapToEntity(postDto);

        // now will call postrepo to save data in database
        Post newPost = postRepository.save(post); // --> this return saved entity

        // convert post into dto so we can return
        PostDto postResponse=new PostDto();
        postResponse=mapToDTO(newPost);

        // after creating post we will change entity into dto and will return to controller
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> allPost=postRepository.findAll();
        // we can't return entity directly

        /*
        --> we can use java 8's stream api--> modern way to process sequence
        List<PostDto> allPostDto=new ArrayList<>();
        for(int i=0;i<allPost.size();i++){
            allPostDto.add(allPostDto.get(i));
        }
        */
        return allPost.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    // method to convert entity into DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto=new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getDescription());
        return postDto;
    }

    // method to convert  dto into entity
    private Post mapToEntity(PostDto postDto){
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getDescription());
        return post;
    }
}