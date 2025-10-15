package com.example.quotes.domain.user.service;

import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.common.utils.PasswordEncoder;
import com.example.quotes.domain.user.dto.request.UpdatePasswordRequest;
import com.example.quotes.domain.user.dto.request.UpdateUserRequest;
import com.example.quotes.domain.user.dto.request.WithdrawUserRequest;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandService {

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;

    public User updatePassword(AuthUser authUser, UpdatePasswordRequest request) {

        User user = userQueryService.getUser(authUser);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(request.getPassword());

        return user;
    }

    public User updateUser(AuthUser authUser, UpdateUserRequest request) {

        User user = userQueryService.getUser(authUser);

        user.updateUser(request.getProfileUrl(), request.getNickname());

        return user;
    }

    public User withdrawUser(AuthUser authUser, WithdrawUserRequest request) {

        User user = userQueryService.getUser(authUser);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.updateDeletedAt(LocalDateTime.now());

        return user;
    }
}
