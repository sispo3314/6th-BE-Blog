package leets.blogapplication.repository;

import leets.blogapplication.controller.PostController;
import leets.blogapplication.domain.Post;
import leets.blogapplication.model.PostM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // C
    void create(PostController.Post post);
    // R(전체)
    List<PostController.Post> findAll();
    //R (상세)
    Optional<PostController.Post> findById(String id);
    //U
    PostController.Post update(String id, PostController.Post post);
    //그리고 이미 post에 id가 들어있는데 왜 id를 또 보내야하는거지?
    //D
    void delete(String id);
    //post에 이미 id가 포함되어 있는데 왜 id를 따로 빼서 보내야하는거지?
    //검색
    List<PostController.Post> findByTitle(String title);
    //title로 read


    //얘네들 내가 의도한 대로 작동하는지 확인 한 번만 하기
}