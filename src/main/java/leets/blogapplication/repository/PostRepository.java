package leets.blogapplication.repository;

import leets.blogapplication.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    // C
    Post create(String title, String content);
    // R
    List<Post> findAll();
    Optional<Post> findById(Long id);
    // U
    Post update(Long id, String title, String content);
    // D
    void deleteById(Long id);
}