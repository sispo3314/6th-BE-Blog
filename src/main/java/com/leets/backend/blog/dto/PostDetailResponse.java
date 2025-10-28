package com.leets.backend.blog.dto;

import com.leets.backend.blog.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String authorNickname;
    private String thumbnail;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostDetailResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.thumbnail = post.getThumbnail();
        this.authorNickname = post.getAuthor() != null ? post.getAuthor().getName() : null;
        this.imageUrls = post.getImages() != null
                ? post.getImages().stream()
                .map(image -> image.getImageUrl()) // ✅ 수정 완료
                .collect(Collectors.toList())
                : null;
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthorNickname() { return authorNickname; }
    public String getThumbnail() { return thumbnail; }
    public List<String> getImageUrls() { return imageUrls; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
