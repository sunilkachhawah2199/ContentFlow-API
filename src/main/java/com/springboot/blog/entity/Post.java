package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//jpa annotation to map with mysql table
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

    // column name in table
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    // set means maintain only unique comment--> if any parameter is different then this is different
    // mappedBy --> post is here in owning relation in bidirectional relation
    // if post not exist then comment will also not exist --> cascast.ALl relation
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments=new HashSet<>();
}