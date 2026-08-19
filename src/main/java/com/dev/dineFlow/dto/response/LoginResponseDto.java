package com.dev.dineFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto
{
    private String token;

    private String email;

    private String userRole;
}
