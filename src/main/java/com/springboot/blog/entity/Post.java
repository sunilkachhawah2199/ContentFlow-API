package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//jpa annotation to map map with mysql table
@Entity

/*his attribute is an array of @UniqueConstraint annotations.
Each @UniqueConstraint annotation defines a unique constraint on the table. */
@Table(
        name = "post", //table name in db
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "title"
                })}
)
public class Post {
    @Id // to specify primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //primary key generation strategy
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;
}