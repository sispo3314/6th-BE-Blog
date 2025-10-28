package com.leets.backend.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class PostCreateRequest {
    @NotBlank @Size(max=200)
    private String title;

    @NotBlank
    private String content;

    private String thumbnail;
    private List<String> imageUrls;
    private Long authorId;

    public Long getAuthorId(){return authorId;}
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getThumbnail(){return thumbnail;}
    public List<String> getImageUrls(){return imageUrls;}
}
