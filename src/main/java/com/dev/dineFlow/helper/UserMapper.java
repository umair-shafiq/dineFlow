package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.response.UserResponseDto;
import com.dev.dineFlow.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper
{
    public UserResponseDto toResponse(User user)
    {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(user.getUserId());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setUserRole(user.getUserRole());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setUserStatus(user.isEnabled());

        return userResponseDto;
    }
}
