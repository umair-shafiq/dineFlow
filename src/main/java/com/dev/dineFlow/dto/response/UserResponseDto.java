package com.dev.dineFlow.dto.response;

import com.dev.dineFlow.entity.enums.UserRoleEnums;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto
{
    private Long userId;

    private String fullName;

    private String email;

    private UserRoleEnums userRole;

    private LocalDateTime createdAt;

    private boolean userStatus;
}
