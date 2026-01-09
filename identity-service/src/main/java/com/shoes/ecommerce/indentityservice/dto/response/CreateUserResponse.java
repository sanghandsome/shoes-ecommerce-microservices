package com.shoes.ecommerce.indentityservice.dto.response;

import com.shoes.ecommerce.indentityservice.common.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateUserResponse {
    private String fullName;
    private String email;
    private String password;
    private UserStatus status;
}
