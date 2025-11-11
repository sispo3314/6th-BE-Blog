package com.leets.backend.blog.service;

import com.leets.backend.blog.auth.JwtTokenProvider;
import com.leets.backend.blog.dto.LoginRequest;
import com.leets.backend.blog.dto.LoginResponse;
import com.leets.backend.blog.dto.SignupRequest;
import com.leets.backend.blog.entity.Token;
import com.leets.backend.blog.entity.User;
import com.leets.backend.blog.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider; // 네가 쓰는 구현 재사용
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtProvider,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.tokenService = tokenService;
    }


    public void register(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.existsByNick(request.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        String encoded = passwordEncoder.encode(request.getPassword());

        User user = User.createLocal(request.getEmail(), encoded, request.getNickname(), request.getName());
        userRepository.save(user);
    }


    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPas())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(user.getEmail());
        String refreshTokenPlain = tokenService.issueRefreshToken(user);

        return new LoginResponse(accessToken, refreshTokenPlain);
    }

    public String refreshAccess(String refreshPlain) {
        Optional<Token> tokenOpt = tokenService.verifyRefreshToken(refreshPlain);
        if (tokenOpt.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        User user = tokenOpt.get().getUser();
        return jwtProvider.createAccessToken(user.getEmail());
    }


    public void logout(String refreshPlain) {
        tokenService.revoke(refreshPlain);
    }
}