package leets.blogapplication.controller;

import leets.blogapplication.model.Post;
import leets.blogapplication.repository.InMemoryPostRepository;
import leets.blogapplication.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) { this.postService = postService; }

    // 목록
    @GetMapping("/posts")
    public String list(Model model) {
        model.addAttribute("posts", postService.getAll());
        return "posts";
    }

    // 작성 폼
    @GetMapping("/post/new")
    public String newForm(Model model) {
        model.addAttribute("post", new Post());
        return "new-post";
    }

    // 작성 처리
    @PostMapping("/post/new")
    public String create(@ModelAttribute Post post) {
        Post saved = postService.create(post.getTitle(), post.getContent());
        // 목록으로 이동 (원하면 상세로 변경 가능)
        return "redirect:/posts";
    }

    // 상세, 프론트 수준에서 구현되지 않음
    @GetMapping("/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getById(id));
        return "post-detail"; // 템플릿 만들면 사용
    }

    // 수정, 프론트 수준에서 구현되지 않음
    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getById(id));
        return "edit-post"; // 템플릿 만들면 사용
    }

    // 수정처리, 프론트 수준에서 구현되지 않음
    @PostMapping("/posts/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Post post) {
        postService.update(id, post.getTitle(), post.getContent());
        return "redirect:/posts/{id}";
    }

    // 삭제, 프론트 수준에서 구현하지 않음
    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }
}