package com.example.quotes.domain.auth.controller;

import com.example.quotes.common.utils.PasswordEncoder;
import com.example.quotes.domain.auth.dto.request.SignInUserRequest;
import com.example.quotes.domain.auth.dto.request.SignUpUserRequest;
import com.example.quotes.domain.auth.dto.response.SignInUserResponse;
import com.example.quotes.domain.auth.dto.response.SignUpUserResponse;
import com.example.quotes.domain.auth.service.AuthService;
import com.example.quotes.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpUserResponse> signUpUser(
            @Valid @RequestBody SignUpUserRequest request) {

        User user = authService.signUp(request.getEmail(), request.getPassword(), request.getUserRole(), request.getProfileUrl(), request.getNickname());
        SignUpUserResponse signUpUserResponse = SignUpUserResponse.of(user.getEmail(), user.getUserRole(), user.getProfileUrl(), user.getNickname());
        return ResponseEntity.ok(signUpUserResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInUserResponse> signInUser(
            @Valid @RequestBody SignInUserRequest request) {

        String token = authService.signIn(request.getEmail(), request.getPassword());
        SignInUserResponse signInUserResponse = SignInUserResponse.of(token);
        return ResponseEntity.ok(signInUserResponse);
    }
}
