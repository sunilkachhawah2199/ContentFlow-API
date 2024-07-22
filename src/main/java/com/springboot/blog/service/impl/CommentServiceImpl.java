package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    // comment Jpa repo instance
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
    }
//    -------------------------------------------------------------

    // add comment to post
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);

        // retrieve post by id or if not available then throw resource not found exception
        Post post= postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id", postId));

        // we are saving post id in comment, so we have to pass post here
        comment.setPost(post);

        // saving comment
        Comment savedComment=commentRepository.save(comment);

        // convert --> entity into dto
        CommentDto savedDto=mapToDto(savedComment);
        return  savedDto;

    }
//----------------------------------------------------------------------
    // method--> fetch all comment for particular post
    @Override
    public List<CommentDto> getAllComment(long postId) {

        // check post with postId exist ot not
        Post fetchPost=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","postId", postId));
        List<Comment> comments=commentRepository.findByPostId(postId);
        List<CommentDto> res=comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return  res;
    }
//    -------------------------------------------------------------------

    // mapping methods--> Dto to Entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setMessage(commentDto.getMessage());
        return  comment;
    }

    // mapping method --> entity to Dto
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setMessage(comment.getMessage());
        return  commentDto;
    }
}