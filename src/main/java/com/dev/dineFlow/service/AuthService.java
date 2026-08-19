package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.request.LoginRequestDto;
import com.dev.dineFlow.dto.response.LoginResponseDto;
import com.dev.dineFlow.entity.User;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.repository.UserRepository;
import com.dev.dineFlow.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LoginResponseDto login(LoginRequestDto loginRequestDto)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getUserRole().name())
                .build();

        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponseDto(token, user.getEmail(), user.getUserRole().name());
    }
}
