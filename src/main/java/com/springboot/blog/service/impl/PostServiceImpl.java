package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
// we save data into db repository which extends jpa repo. have all queries related method in this so we use this is service layer
    private PostRepository postRepository;

    // instance of model mapper
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }


    // method to create post which take psotDto as input
    @Override
    public PostDto createPost(PostDto postDto) {
        // convert dto into entity
        Post post=new Post();
        post=mapToEntity(postDto);

        // now will call postrepo to save data in database
        Post newPost = postRepository.save(post); // --> this return saved entity

        // convert post into dto so we can return
        PostDto postResponse;
        postResponse=mapToDTO(newPost);

        // after creating post we will change entity into dto and will return to controller
        return postResponse;
    }

//-----------------------------------------------------------------------------
    // get all posts without pagination
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

//-------------------------------------------------------------------------------
    // get all post with pagination and sorting
    public PostResponse getAllPostPagination(int pageNo, int pageSize, String sortBy, String sortDir) {

        // dynamic sorting feature
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // pageable Instance
        Pageable pageable=PageRequest.of(pageNo, pageSize, sort); // sort in ascending
         // pageable=PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending()); // sort in descending

        // passing pageable instance to find all
        Page<Post> posts=postRepository.findAll(pageable);

        // get content from page object | getContent()--> return type list
        List<Post> listOfPosts=posts.getContent();

        List<PostDto> content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        // post response instance
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }


//    ----------------------------------------------------------------------------
    // get post by id
    @Override
    public PostDto getPostById(Long id) {
        // find by provide optional class
        Post post=postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id", id));
        //convert entity into dto
        return mapToDTO(post);
    }

    // update post by id
    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        // for upadting post first we will get post
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("post", "id", id));

        // post attribution updated
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(post.getDescription());

        Post updatedPost=postRepository.save(post);
        // changing entity into dto
        PostDto updatedDto=mapToDTO(updatedPost);
        return updatedDto;
    }

    // delete post by id
    public void deletePost(Long id){
        // first get post by id, throw error if resource not found
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post", "id", id));
        // delete post which we fetched
        postRepository.delete(post);
    }


    // method to convert entity into DTO using model mapper
    private PostDto mapToDTO(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    // method to convert  dto into entity
    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}