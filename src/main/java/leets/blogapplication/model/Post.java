package leets.blogapplication.model;

import java.time.LocalDateTime;

public class Post {
    private Long id;                 // Repository에서 발급
    private String title;
    private String content;
    private LocalDateTime createdAt; // 생성 시각
    private LocalDateTime updatedAt; // 최초에는 null, 수정 시에만 값 셋팅

    public Post() { } // 타임리프/스프링 바인딩용 기본 생성자

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // --- getters/setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
