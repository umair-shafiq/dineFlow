package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.request.UserRequestDto;
import com.dev.dineFlow.dto.request.UserUpdateRequestDto;
import com.dev.dineFlow.dto.response.UserResponseDto;
import com.dev.dineFlow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController
{
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto)
    {
        return new ResponseEntity<>(userService.registerUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id)
    {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam String email)
    {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponseDto> toggleUserStatus(@PathVariable Long id, @RequestParam boolean enabled)
    {
        return new ResponseEntity<>(userService.toggleUserStatus(id, enabled), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto requestDto)
    {
        return new ResponseEntity<>(userService.updateUser(id, requestDto), HttpStatus.OK);
    }
}
