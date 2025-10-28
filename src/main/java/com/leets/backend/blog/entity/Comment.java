package com.leets.backend.blog.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="comment")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="content_id", nullable=false)
    private Post post;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User author;

    @Lob @Column(name="content", nullable = false)
    private String content;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public Comment(){}

    public static Comment create(Post post, User author, String content) {
        Comment comment = new Comment();
        comment.post = post;
        comment.author = author;
        comment.content = content;
        comment.createdAt = LocalDateTime.now();
        comment.updatedAt = LocalDateTime.now();
        return comment;
    }

    public void updateContent(String newContent){
        this.content=newContent;
        this.updatedAt=LocalDateTime.now();
    }

    public Long getId(){return id;}
    public Post getPost(){return post;}
    public User getAuthor(){return author;}
    public String getContent(){return content;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public LocalDateTime getUpdatedAt(){return updatedAt;}
}
