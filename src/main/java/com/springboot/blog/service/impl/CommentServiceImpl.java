package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

//    --------------------------------------------------------------------
    // method --> get comment by id for specific post
    @Override
    public CommentDto getCommentById(long commentId, long postId) {

        // first find post for which we are trying to get comment
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post","postId", postId));

        // then find comment with comment Id
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment","commentId",commentId));

        // now check if comment belong to same post or not
        if(!Objects.equals(comment.getPost().getId(), postId)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");
        }


        return mapToDto(comment);
    }

//    ------------------------------------------------------------

    // update comment by post id
    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        // get post by id
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));

        // get comment by id
        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment id", commentId));

        // check --> comment belong to same post
        if(!Objects.equals(comment.getPost().getId(), postId)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");
        }

        // now everything is ok update comment
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setMessage(commentDto.getMessage());

        Comment updatedComment=commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        // check post validity
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));

        // check comment validity
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));

        // check comment belong to this post
        if(!Objects.equals(comment.getPost().getId(), postId)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment not belong to this post");
        }

        // delete comment
        commentRepository.deleteById(commentId);
    }
//    -------------------------------------------------------------------

    // delete comment


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