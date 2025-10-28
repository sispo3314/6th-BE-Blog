package com.leets.backend.blog.dto;

public class CommentCreateRequest {
    private Long postId;
    private Long userId;
    private String content;

    public CommentCreateRequest(){}

    public CommentCreateRequest(Long postId, Long userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    public Long getPostId() {return postId;}
    public Long getUserId(){return userId;}
    public String getContent(){return content;}
}
