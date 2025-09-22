package com.leets.backend.blog.repository;

import com.leets.backend.blog.model.Post;
import java.util.*;

public interface PostRepository{
    Post save(Post post);
    Optional<Post> findById(Long id);
    List<Post> findAll();
}