package com.leets.backend.blog.service;

import com.leets.backend.blog.auth.TokenHash;
import com.leets.backend.blog.entity.Token;
import com.leets.backend.blog.entity.User;
import com.leets.backend.blog.repository.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    //리프레시 토큰 만료기간
    private static final int REFRESH_DAYS = 14;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String issueRefreshToken(User user) {
        String plain = generateRandomToken();
        byte[] hash = sha256(plain);

        Token t = new Token();
        setTokenFields(t, user, hash, LocalDateTime.now(),
                LocalDateTime.now().plusDays(REFRESH_DAYS));

        tokenRepository.save(t);
        return plain;
    }

    public Optional<Token> verifyRefreshToken(String plain) {
        byte[] hash = sha256(plain);
        Optional<Token> opt = tokenRepository.findByTokenHash(hash);
        if (opt.isEmpty()) return opt;
        Token t = opt.get();
        if (t.getExpiredAt() != null && t.getExpiredAt().isBefore(LocalDateTime.now())) {
            // 만료된 토큰은 제거
            tokenRepository.delete(t);
            return Optional.empty();
        }
        return opt;
    }

    public void revoke(String plain) {
        verifyRefreshToken(plain).ifPresent(tokenRepository::delete);
    }

    private String generateRandomToken() {
        byte[] buf = new byte[32]; // 256-bit
        secureRandom.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }

    private byte[] sha256(String v) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(v.getBytes(StandardCharsets.UTF_8)); // 32 bytes
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    private void setTokenFields(Token t, User user, byte[] hash, LocalDateTime issued, LocalDateTime expired) {
        try {
            var userF = Token.class.getDeclaredField("user");
            var tokenHashF = Token.class.getDeclaredField("tokenHash");
            var issuedAtF = Token.class.getDeclaredField("issuedAt");
            var expiredAtF = Token.class.getDeclaredField("expiredAt");
            userF.setAccessible(true);
            tokenHashF.setAccessible(true);
            issuedAtF.setAccessible(true);
            expiredAtF.setAccessible(true);
            userF.set(t, user);
            tokenHashF.set(t, hash);
            issuedAtF.set(t, issued);
            expiredAtF.set(t, expired);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Token 엔티티에 세터가 없어 필드 주입에 실패했습니다.", e);
        }
    }
}
