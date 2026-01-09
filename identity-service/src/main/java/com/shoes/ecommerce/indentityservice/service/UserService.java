package com.shoes.ecommerce.indentityservice.service;

import com.shoes.ecommerce.commonlib.exception.AppException;
import com.shoes.ecommerce.commonlib.exception.ErrorCode;
import com.shoes.ecommerce.indentityservice.common.UserStatus;
import com.shoes.ecommerce.indentityservice.dto.request.CreateUserRequest;
import com.shoes.ecommerce.indentityservice.dto.response.CreateUserResponse;
import com.shoes.ecommerce.indentityservice.entity.User;
import com.shoes.ecommerce.indentityservice.mapper.UserMapper;
import com.shoes.ecommerce.indentityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }

        var user = User.builder()
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .fullName(createUserRequest.getFullName())
                .status(UserStatus.ACTIVE)
                .build();
        User userCreate = userMapper.toUserFromCreate(createUserRequest);
        userRepository.save(userCreate);
    }
}
