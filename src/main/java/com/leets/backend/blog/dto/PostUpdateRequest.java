package com.leets.backend.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class PostUpdateRequest {

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    private String content;

    private String thumbnail;
    private List<String> imageUrls;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getThumbnail() { return thumbnail; }
    public List<String> getImageUrls() { return imageUrls; }
}
