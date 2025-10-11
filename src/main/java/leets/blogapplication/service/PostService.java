package leets.blogapplication.service;

import leets.blogapplication.controller.PostController;
import leets.blogapplication.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostController.Post> getAll() { return postRepository.findAll(); }

    public PostController.Post getById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 ID: " + id));
    }

    public PostController.Post create(String title, String content) {
        if (title.length() > 100) throw new IllegalArgumentException("제목은 100자 이하만 허용됩니다.");
        return postRepository.create(title, content);
    }
    //create 하고서는 따로 return 할 필요 없음

    public PostController.Post update(String id, PostController.Post post) {
        return postRepository.update(id, post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
    public PostController.Post getPostById(String id) { return postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("PostM not found")); }
    public PostController.Post createPost(PostController.Post post) { return postRepository.create(post); }
    public PostController.Post updatePost(String id, PostController.Post post) { return postRepository.update(id, post); }
    public void deletePost(String id) { postRepository.delete(id); }
    public List<PostController.Post> getPostByTitle(String title) { return postRepository.findByTitle(title); }
}