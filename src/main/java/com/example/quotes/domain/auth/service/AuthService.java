package com.example.quotes.domain.auth.service;

import com.example.quotes.common.enums.UserRole;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.common.jwt.JwtUtil;
import com.example.quotes.common.utils.PasswordEncoder;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.repository.UserRepository;
import com.example.quotes.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User signUp(String email, String password, UserRole userRole,String profileUrl, String nickname)  {

        if (userRepository.existsByEmail(email)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 가입되어있는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.create(email, encodedPassword, userRole, profileUrl, nickname);
        return userRepository.save(user);
    }

    public String signIn(String email, String password) {

        User user = userQueryService.getUserByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(user.getId(), user.getEmail(), user.getUserRole());
    }
}
