package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
//jpa repo is generic type we pass entity and primary key
//post is Entity || Long is type for primary key

//jpa repo internally extends ListPagingAndSortingRepository for sorting and pagination
public interface PostRepository extends JpaRepository<Post, Long> {

}