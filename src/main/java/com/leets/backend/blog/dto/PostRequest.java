package com.leets.backend.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class PostRequest {
    @NotBlank @Size(max=200)
    private String title;

    @NotBlank
    private String content;

    private String thumbnail;
    private List<String> imageUrls;
    private Long authorId;

    public Long getAuthorId(){return authorId;}
    public void setAuthorId(Long authorId){this.authorId = authorId;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}
    public String getContent(){return content;}
    public void setContent(String content){this.content = content;}
    public String getThumbnail(){return thumbnail;}
    public void setThumbnail(String thumbnail){this.thumbnail = thumbnail;}
    public List<String> getImageUrls(){return imageUrls;}
    public void setImageUrls(List<String> imageUrls){this.imageUrls = imageUrls;}
}
