package com.leets.backend.blog.service;

import com.leets.backend.blog.entity.*;
import com.leets.backend.blog.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment create(Long postId, Long userId, String content){
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글을 찾을 수 없어요"));
        User user=userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("해당 유저를 찾을 수 없어요"));

        Comment comment=Comment.create(post, user, content);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentByPost(Long postId){
        return commentRepository.findByPost_IdOrderByCreatedAtAsc(postId);
    }

    public Comment update(Long commentId, Long userId, String newContent){
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않아요"));

        if(!comment.getAuthor().getId().equals(userId)){
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있어요");
        };

        comment.updateContent(newContent);
        return comment;
    }

    public void delete(Long commentId, Long userId){
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않아요"));

        if(!comment.getAuthor().getId().equals(userId)){
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있어요");
        }

        commentRepository.delete(comment);
    }

}
