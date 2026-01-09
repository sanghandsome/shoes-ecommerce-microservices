package com.shoes.ecommerce.indentityservice.mapper;

import com.shoes.ecommerce.indentityservice.dto.request.CreateUserRequest;
import com.shoes.ecommerce.indentityservice.dto.response.CreateUserResponse;
import com.shoes.ecommerce.indentityservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserFromCreate(CreateUserRequest createUserRequest);
    CreateUserResponse toCreateUserResponse(User user);
}
