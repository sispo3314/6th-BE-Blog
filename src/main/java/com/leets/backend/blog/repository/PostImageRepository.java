package com.leets.backend.blog.repository;

import com.leets.backend.blog.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPost_IdOrderByOrderAsc(Long postId);
}
