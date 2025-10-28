package com.leets.backend.blog.service;

import com.leets.backend.blog.dto.PostRequest;
import com.leets.backend.blog.dto.PostDetailResponse;
import com.leets.backend.blog.entity.Post;
import com.leets.backend.blog.entity.PostImage;
import com.leets.backend.blog.entity.User;
import com.leets.backend.blog.repository.PostRepository;
import com.leets.backend.blog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /** 전체 게시글 조회 */
    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        return new PostDetailResponse(post);
    }

    public PostDetailResponse create(PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setThumbnail(request.getThumbnail());


        User author;
        if (request.getAuthorId() != null) {
            author = userRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        } else {
            author = userRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("기본 유저(1L)를 찾을 수 없습니다."));
        }
        post.setAuthor(author);

        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            for (int i = 0; i < request.getImageUrls().size(); i++) {
                PostImage image = new PostImage();
                image.setImageUrl(request.getImageUrls().get(i));
                image.setPost(post);
                post.getImages().add(image);
            }
        }

        Post saved = postRepository.save(post);
        return new PostDetailResponse(saved);
    }


    public PostDetailResponse update(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setThumbnail(request.getThumbnail());

        // 기존 이미지 제거 후 다시 등록
        post.getImages().clear();
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            for (int i = 0; i < request.getImageUrls().size(); i++) {
                PostImage image = new PostImage();
                image.setImageUrl(request.getImageUrls().get(i));
                image.setPost(post);
                post.getImages().add(image);
            }
        }

        return new PostDetailResponse(post);
    }


    public void delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }
        postRepository.deleteById(id);
    }
}
