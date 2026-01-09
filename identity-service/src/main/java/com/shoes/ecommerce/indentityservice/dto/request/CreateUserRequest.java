package com.shoes.ecommerce.indentityservice.dto.request;


import com.shoes.ecommerce.indentityservice.common.UserStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateUserRequest {
    private String fullName;
    private String email;
    private String password;
    private UserStatus status;
}
