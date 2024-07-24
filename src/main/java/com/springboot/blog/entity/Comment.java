package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.StringReader;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity // to make class Jpa entity
// no unique constraints --> I am allowing multiple comments from same user
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "message", nullable = false)
    private String message; //posted comment by user

    // we need to create 1 to many relation between post and comment
    // FetchType.Lazy --> it means that the related data will not be loaded from the database until it is accessed for the first time (i.e., it is loaded on demand).

    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn --> used for foreign key
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}