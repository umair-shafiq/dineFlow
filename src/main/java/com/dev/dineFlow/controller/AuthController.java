package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.request.LoginRequestDto;
import com.dev.dineFlow.dto.response.LoginResponseDto;
import com.dev.dineFlow.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
}
