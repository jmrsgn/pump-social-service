package com.johnmartin.social.mapper;

import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.entities.UserEntity;

public class UserMapper {

    public static UserResponse toResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userResponse.getId());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setProfileImageUrl(userEntity.getProfileImageUrl());
        userResponse.setBio(userEntity.getBio());
        userResponse.setFollowersNo(userResponse.getFollowersNo());
        userResponse.setFollowingNo(userResponse.getFollowingNo());
        return userResponse;
    }
}
