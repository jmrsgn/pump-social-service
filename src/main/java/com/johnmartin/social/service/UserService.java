package com.johnmartin.social.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.johnmartin.social.constants.error.AuthErrorConstants;
import com.johnmartin.social.constants.error.domain.UserErrorConstants;
import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.dto.request.CreateUserRequest;
import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.entities.UserEntity;
import com.johnmartin.social.exception.NotFoundException;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.mapper.UserMapper;
import com.johnmartin.social.repository.UserRepository;
import com.johnmartin.social.security.AuthContext;
import com.johnmartin.social.utilities.LoggerUtility;

@Service
public class UserService {

    private static final Class<UserService> clazz = UserService.class;

    private final UserRepository userRepository;
    private final UserFollowService userFollowService;

    public UserService(UserRepository userRepository, UserFollowService userFollowService) {
        this.userRepository = userRepository;
        this.userFollowService = userFollowService;
    }

    public UserEntity findById(String userId) {
        LoggerUtility.d(clazz, "Execute method: [findById]");
        return userRepository.findById(userId)
                             .orElseThrow(() -> new NotFoundException(UserErrorConstants.USER_NOT_FOUND));
    }

    public UserResponse getUserById(String userId) {
        LoggerUtility.d(clazz, "Execute method: [getUserById]");
        UserEntity user = findById(userId);
        return UserMapper.toResponse(user, false);
    }

    public UserResponse createUser(CreateUserRequest request) {
        LoggerUtility.d(clazz, "Execute method: [createUser]");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(request.userId());
        userEntity.setFirstName(request.firstName());
        userEntity.setLastName(request.lastName());
        userEntity.setEmail(request.email());
        userEntity.setProfileImageUrl(StringUtils.EMPTY);
        userEntity.setBio(StringUtils.EMPTY);

        UserEntity createdUser = userRepository.save(userEntity);
        return UserMapper.toResponse(createdUser, false);
    }

    public UserResponse followUser(String userId) {
        LoggerUtility.d(clazz, String.format("Execute method: [followUser] userId: [%s]", userId));

        AuthUser authUser = AuthContext.get();
        if (authUser == null) {
            LoggerUtility.d(clazz, "Auth user is null, will throw unauthorized exception");
            throw new UnauthorizedException(AuthErrorConstants.USER_IS_NOT_AUTHENTICATED);
        }

        boolean isFollowing = userFollowService.toggleFollow(authUser.id(), userId);
        UserEntity followedUser = findById(userId);
        LoggerUtility.d(clazz, String.format("followedUser: [%s]", followedUser));
        return UserMapper.toResponse(followedUser, isFollowing);
    }
}
