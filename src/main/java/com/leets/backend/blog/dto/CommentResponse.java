package com.leets.backend.blog.dto;

import com.leets.backend.blog.entity.Comment;
import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private String authorName;
    private String authorNick;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.userId = comment.getAuthor().getId();
        this.authorName = comment.getAuthor().getName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getPostId() { return postId; }
    public Long getUserId() { return userId; }
    public String getAuthorName() { return authorName; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
