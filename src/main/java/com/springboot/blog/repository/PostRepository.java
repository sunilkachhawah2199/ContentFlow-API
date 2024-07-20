package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
//jpa repo is generic type we pass entity and primary key
//post is Entity || Long is type for primary key


public interface PostRepository extends JpaRepository<Post, Long> {

}