package com.leets.backend.blog.controller;

import com.leets.backend.blog.common.ApiResponse;
import com.leets.backend.blog.dto.PostDetailResponse;
import com.leets.backend.blog.dto.PostRequest;
import com.leets.backend.blog.dto.PostResponse;
import com.leets.backend.blog.entity.Post;
import com.leets.backend.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시물", description = "게시물 CRUD REST API")
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 목록 (현재 PostService#getPosts() 시그니처에 맞춰서 DTO 변환만 컨트롤러에서 수행)
    @Operation(summary = "게시물 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> list() {
        List<Post> posts = postService.getPosts();
        List<PostResponse> body = posts.stream()
                .map(PostResponse::new) // 엔티티 -> DTO 생성자
                .toList();
        return ResponseEntity.ok(ApiResponse.success(body));
    }

    // 상세
    @Operation(summary = "게시물 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(postService.getPost(id)));
    }

    // 생성
    @Operation(summary = "게시물 생성")
    @PostMapping
    public ResponseEntity<ApiResponse<PostDetailResponse>> create(@Valid @RequestBody PostRequest request) {
        PostDetailResponse created = postService.create(request); // PostService 시그니처에 맞춤
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "게시물이 생성되었습니다."));
    }

    // 수정 (전체/부분 동일 DTO 사용 시)
    @Operation(summary = "게시물 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest request
    ) {
        PostDetailResponse updated = postService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(updated, "게시물이 수정되었습니다."));
    }

    // 삭제
    @Operation(summary = "게시물 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "게시물이 삭제되었습니다."));
    }
}
