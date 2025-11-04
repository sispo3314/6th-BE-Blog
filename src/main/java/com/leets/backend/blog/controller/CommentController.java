package com.leets.backend.blog.controller;

import com.leets.backend.blog.dto.CommentCreateRequest;
import com.leets.backend.blog.dto.CommentResponse;
import com.leets.backend.blog.dto.CommentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.leets.backend.blog.entity.Comment;
import com.leets.backend.blog.service.CommentService;
import java.util.List;

@Tag(name = "댓글", description = "댓글 CRUD REST API (posts 하위 리소스)")
@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary="특정 게시물의 댓글 목록 조회")
    @GetMapping
    public List<CommentResponse> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentResponsesByPost(postId);
    }

    @Operation(summary="댓글 생성")
    @PostMapping
    public CommentResponse create(@PathVariable Long postId,
                                  @RequestBody CommentCreateRequest request) {
        Comment comment = commentService.create(
                postId,
                request.getUserId(),
                request.getContent()
        );
        return new CommentResponse(comment);
    }

    @Operation(summary="댓글 수정")
    @PatchMapping("/{commentId}")
    public CommentResponse update(@PathVariable Long postId,
                                  @PathVariable Long commentId,
                                  @RequestBody CommentUpdateRequest request) {
        Comment updated = commentService.update(
                postId,
                commentId,
                request.getUserId(),
                request.getNewContent()
        );
        return new CommentResponse(updated);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long postId,
                       @PathVariable Long commentId,
                       @RequestParam Long userId) {
        commentService.delete(postId, commentId, userId);
    }
}
