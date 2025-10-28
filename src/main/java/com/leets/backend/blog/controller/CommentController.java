package com.leets.backend.blog.controller;

import com.leets.backend.blog.dto.CommentCreateRequest;
import com.leets.backend.blog.dto.CommentResponse;
import com.leets.backend.blog.dto.CommentUpdateRequest;
import org.springframework.web.bind.annotation.*;
import com.leets.backend.blog.entity.Comment;
import com.leets.backend.blog.service.CommentService;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentByPost(postId);
    }

    @PostMapping
    public CommentResponse create(@RequestBody CommentCreateRequest request) {
        Comment comment = commentService.create(
                request.getPostId(),
                request.getUserId(),
                request.getContent()
        );
        return new CommentResponse(comment);
    }

    @PatchMapping("/{commentId}")
    public CommentResponse update(@PathVariable Long commentId,
                                  @RequestBody CommentUpdateRequest request) {
        Comment updated = commentService.update(
                commentId,
                request.getUserId(),
                request.getNewContent()
        );
        return new CommentResponse(updated);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId,
                       @RequestParam Long userId) {
        commentService.delete(commentId, userId);
    }

}
