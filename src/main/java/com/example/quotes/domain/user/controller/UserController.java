package com.example.quotes.domain.user.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.user.dto.request.UpdatePasswordRequest;
import com.example.quotes.domain.user.dto.request.UpdateUserRequest;
import com.example.quotes.domain.user.dto.request.WithdrawUserRequest;
import com.example.quotes.domain.user.dto.response.UserResponse;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserCommandService;
import com.example.quotes.domain.user.service.UserQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @GetMapping("/users")
    public ResponseEntity<UserResponse> getUser(@Auth AuthUser authUser) {

        User user = userQueryService.getUserById(authUser.getUserId());
        UserResponse userResponse = UserResponse.of(user.getEmail(), user.getUserRole(), user.getProfileUrl(), user.getNickname());
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUser(
            @Auth AuthUser authUser,
            @Valid @RequestBody UpdateUserRequest request) {

        User user = userCommandService.updateUser(authUser.getUserId(), request.getProfileUrl(), request.getNickname());
        UserResponse userResponse = UserResponse.of(user.getEmail(), user.getUserRole(), user.getProfileUrl(), user.getNickname());
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/users")
    public ResponseEntity<String> updatePassword(
            @Auth AuthUser authUser,
            @Valid @RequestBody UpdatePasswordRequest request) {

        userCommandService.updatePassword(authUser.getUserId(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 업데이트 되었습니다.");
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(
            @Auth AuthUser authUser,
            @Valid @RequestBody WithdrawUserRequest request) {

        userCommandService.withdrawUser(authUser.getUserId(), request.getPassword());
        return ResponseEntity.ok("회원탈퇴가 성공적으로 되었습니다.");
    }
}
