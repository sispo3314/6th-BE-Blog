package com.leets.backend.blog.repository;

import com.leets.backend.blog.entity.Token;
import com.leets.backend.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenHash(byte[] tokenHash);
    List<Token> findByUser(User user);
    long deleteByExpiredAtBefore(LocalDateTime expiredAt);
    long deleteByUser(User user);
}
