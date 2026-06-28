package com.johnmartin.social.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.johnmartin.social.constants.error.domain.UserErrorConstants;
import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.dto.request.CreateUserRequest;
import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.dto.response.UserSummaryResponse;
import com.johnmartin.social.entity.UserEntity;
import com.johnmartin.social.exceptions.NotFoundException;
import com.johnmartin.social.mapper.UserMapper;
import com.johnmartin.social.repository.UserRepository;
import com.johnmartin.social.utilities.LoggerUtility;

@Service
public class UserService {

    private static final Class<UserService> clazz = UserService.class;

    private final UserRepository userRepository;

    private final UserFollowService userFollowService;
    private final AuthService authService;

    public UserService(UserRepository userRepository, UserFollowService userFollowService, AuthService authService) {
        this.userRepository = userRepository;
        this.userFollowService = userFollowService;
        this.authService = authService;
    }

    public UserEntity findById(String userId) {
        LoggerUtility.d(clazz, "Execute method: [findById]");
        return userRepository.findById(userId)
                             .orElseThrow(() -> new NotFoundException(UserErrorConstants.USER_NOT_FOUND));
    }

    public List<UserEntity> findByIdIn(List<String> userIds) {
        LoggerUtility.d(clazz, "Execute method: [findByIdIn]");
        return userRepository.findByIdIn(userIds);
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

        AuthUser authUser = authService.getAuthUser();

        boolean isFollowing = userFollowService.toggleFollow(authUser.id(), userId);
        UserEntity followedUser = findById(userId);
        LoggerUtility.d(clazz, String.format("followedUser: [%s]", followedUser));
        return UserMapper.toResponse(followedUser, isFollowing);
    }

    /**
     * This is used for coaching-service to map all user info to user profile as client
     * 
     * @param userIds
     *            - List of user IDs
     * @return List<UserSummaryResponse>
     */
    public List<UserSummaryResponse> getUsersByIds(List<String> userIds) {
        LoggerUtility.d(clazz, String.format("Execute method: [getUsersByIds] userIds size: [%s]", userIds.size()));

        // Get users under user IDs
        List<UserEntity> users = userRepository.findByIdIn(userIds);
        LoggerUtility.logItemSize(clazz, "users", users);

        return users.stream().map(UserMapper::toSummaryResponse).toList();
    }

    /**
     * Search users
     * 
     * @param query
     *            - Filter
     * @return List<UserSummaryResponse>
     */
    public List<UserSummaryResponse> searchUsers(String query) {
        LoggerUtility.d(clazz, String.format("Execute method: [searchUsers] query: [%s]", query));

        if (StringUtils.isBlank(query)) {
            return Collections.emptyList();
        }

        List<UserEntity> users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query,
                                                                                                                  query);

        LoggerUtility.logItemSize(clazz, "users", users);
        List<UserSummaryResponse> userSummaryResponseList = users.stream().map(UserMapper::toSummaryResponse).toList();
        LoggerUtility.logItemSize(clazz, "userSummaryResponseList", userSummaryResponseList);
        return userSummaryResponseList;
    }
}
