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

        User author=userRepository.findById(request.getAuthorId())
                .orElseThrow(()->new IllegalArgumentException("작성자를 찾을 수 없어요:"+request.getAuthorId()));
            post.setAuthor(author);

        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            for (int i = 0; i < request.getImageUrls().size(); i++) {
                PostImage image = new PostImage();
                image.setImageUrl(request.getImageUrls().get(i));
                image.setPost(post);
                // image.setOrder(i + 1); // order 필드 쓸 경우
                post.getImages().add(image);
            }
        }

        Post saved = postRepository.save(post);
        return new PostDetailResponse(saved);
    }

    /** 게시글 수정 */
    public PostDetailResponse update(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setThumbnail(request.getThumbnail());

        post.getImages().clear();
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            for (int i = 0; i < request.getImageUrls().size(); i++) {
                PostImage image = new PostImage();
                image.setImageUrl(request.getImageUrls().get(i));
                image.setPost(post);
                // image.setOrder(i + 1);
                post.getImages().add(image);
            }
        }

        return new PostDetailResponse(post); // JPA 더티체킹으로 자동 업데이트
    }

    /** 게시글 삭제 */
    public void delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }
        postRepository.deleteById(id);
    }
}