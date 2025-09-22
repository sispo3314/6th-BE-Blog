package leets.blogapplication.repository;

import leets.blogapplication.model.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPostRepository implements PostRepository {
    // 💾 HashMap 저장소 (동시성 고려)
    private final Map<Long, Post> store = new ConcurrentHashMap<>();
    // 🔑 Key 생성 책임: Repository / Key 타입: Long / 중복방지: AtomicLong 자동증가
    private final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Post create(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title은 비어 있을 수 없습니다.");
        }
        long id = sequence.incrementAndGet();
        Post post = new Post(title, content);
        post.setId(id);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null); // 요구사항: 최초엔 null로 두기
        store.put(id, post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        List<Post> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparing(Post::getId));
        return list;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Post update(Long id, String title, String content) {
        Post existing = store.get(id);
        if (existing == null) throw new NoSuchElementException("존재하지 않는 ID: " + id);
        if (title != null && !title.isBlank()) existing.setTitle(title);
        if (content != null) existing.setContent(content);
        existing.setUpdatedAt(LocalDateTime.now()); // 수정 시점 기록
        return existing;
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
