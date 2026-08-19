package com.dev.dineFlow.dto.request;

import com.dev.dineFlow.entity.enums.UserRoleEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequestDto
{
    @NotBlank
    private String fullName;

    @Size(min = 6)
    private String password;

    @NotNull
    private UserRoleEnums userRole;
}
