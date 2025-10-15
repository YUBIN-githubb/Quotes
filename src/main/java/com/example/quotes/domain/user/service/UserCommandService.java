package com.example.quotes.domain.user.service;

import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.common.utils.PasswordEncoder;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandService {

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;

    public User updatePassword(AuthUser authUser, String oldPassword, String newPassword) {

        User user = userQueryService.getUserById(authUser);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(passwordEncoder.encode(newPassword));

        return user;
    }

    public User updateUser(AuthUser authUser, String profileUrl, String nickname) {

        User user = userQueryService.getUserById(authUser);

        user.updateUser(profileUrl, nickname);

        return user;
    }

    public User withdrawUser(AuthUser authUser, String password) {

        User user = userQueryService.getUserById(authUser);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.updateDeletedAt(LocalDateTime.now());

        return user;
    }
}
