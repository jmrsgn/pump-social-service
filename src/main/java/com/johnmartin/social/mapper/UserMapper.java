package com.johnmartin.social.mapper;

import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.entities.UserEntity;

public class UserMapper {

    public static UserResponse toResponse(UserEntity userEntity) {
        return new UserResponse().withId(userEntity.getId())
                                 .withFirstName(userEntity.getFirstName())
                                 .withLastName(userEntity.getLastName())
                                 .withEmail(userEntity.getEmail())
                                 .withProfileImageUrl(userEntity.getProfileImageUrl())
                                 .withBio(userEntity.getBio())
                                 .withFollowersNo(userEntity.getFollowersNo())
                                 .withFollowingNo(userEntity.getFollowingNo());
    }
}
