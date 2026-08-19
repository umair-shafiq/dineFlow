package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.request.UserRequestDto;
import com.dev.dineFlow.dto.request.UserUpdateRequestDto;
import com.dev.dineFlow.dto.response.UserResponseDto;
import com.dev.dineFlow.entity.User;
import com.dev.dineFlow.exception.DuplicateResourceException;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.UserMapper;
import com.dev.dineFlow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto registerUser(UserRequestDto userRequestDto)
    {
        Optional<User> existingUser = userRepository.findByEmail(userRequestDto.getEmail());

        if (existingUser.isPresent())
        {
            throw new DuplicateResourceException("An account with this email already exists.");
        }

        User user = new User();
        user.setFullName(userRequestDto.getFullName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setUserRole(userRequestDto.getUserRole());

        return userMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponseDto> getAllUsers()
    {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    public UserResponseDto getUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return userMapper.toResponse(user);
    }

    public UserResponseDto getUserByEmail(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return userMapper.toResponse(user);
    }

    public UserResponseDto toggleUserStatus(Long id, boolean status)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        user.setEnabled(status);

        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponseDto updateUser(Long id, UserUpdateRequestDto requestDto)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        user.setFullName(requestDto.getFullName());
        user.setUserRole(requestDto.getUserRole());

        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank())
        {
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }

        return userMapper.toResponse(userRepository.save(user));
    }
}
