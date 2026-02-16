package com.johnmartin.social.mapper;

import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.entities.UserEntity;

public class UserMapper {

    public static UserResponse toResponse(UserEntity userEntity) {
        return new UserResponse(userEntity.getId(),
                                userEntity.getFirstName(),
                                userEntity.getLastName(),
                                userEntity.getEmail(),
                                userEntity.getPhone(),
                                userEntity.getRole(),
                                userEntity.getProfileImageUrl());
    }
}
