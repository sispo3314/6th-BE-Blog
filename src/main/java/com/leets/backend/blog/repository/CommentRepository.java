package com.leets.backend.blog.repository;

import com.leets.backend.blog.entity.Comment;
import com.leets.backend.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
}
