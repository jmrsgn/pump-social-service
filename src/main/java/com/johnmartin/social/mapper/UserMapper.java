package com.johnmartin.social.mapper;

import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.dto.internal.AuthUserResponse;
import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.entities.UserEntity;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(UserEntity userEntity, boolean isFollowing) {
        return new UserResponse(userEntity.getId(),
                                userEntity.getFirstName(),
                                userEntity.getLastName(),
                                userEntity.getEmail(),
                                userEntity.getProfileImageUrl(),
                                userEntity.getBio(),
                                userEntity.getFollowersCount(),
                                userEntity.getFollowingCount(),
                                isFollowing);
    }

    public static AuthUser toAuthUser(AuthUserResponse authUserResponse) {
        return new AuthUser(authUserResponse.id(),
                            authUserResponse.firstName(),
                            authUserResponse.lastName(),
                            authUserResponse.email(),
                            authUserResponse.phone());
    }
}
