package com.example.quotes.domain.user.service;

import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;

    public User getUser(AuthUser authUser) {
        return userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));
    }

}
