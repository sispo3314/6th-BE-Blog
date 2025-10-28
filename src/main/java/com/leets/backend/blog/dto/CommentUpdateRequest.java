package com.leets.backend.blog.dto;

public class CommentUpdateRequest {
    private Long userId;
    private String newContent;

    public CommentUpdateRequest(){}

    public CommentUpdateRequest(Long userId, String newContent) {
        this.userId = userId;
        this.newContent = newContent;
    }

    public Long getUserId() {return userId;}
    public String getNewContent(){return newContent;}
}
