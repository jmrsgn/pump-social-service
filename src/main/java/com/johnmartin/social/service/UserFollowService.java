package com.johnmartin.social.service;

import org.springframework.stereotype.Service;

import com.johnmartin.social.entities.UserFollowEntity;
import com.johnmartin.social.exception.BadRequestException;
import com.johnmartin.social.repository.UserFollowRepository;
import com.johnmartin.social.repository.UserRepository;
import com.johnmartin.social.utilities.LoggerUtility;

import jakarta.transaction.Transactional;

@Service
public class UserFollowService {

    private static final Class<UserFollowService> clazz = UserFollowService.class;

    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;

    public UserFollowService(UserFollowRepository userFollowRepository, UserRepository userRepository) {
        this.userFollowRepository = userFollowRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean toggleFollow(String followerId, String followingId) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [toggleFollow] followerId: [%s] followingId: [%s]",
                                      followerId,
                                      followingId));

        // Prevent self-follow
        if (followerId.equals(followingId)) {
            throw new BadRequestException("You cannot follow yourself");
        }

        boolean alreadyFollowing = userFollowRepository.existsByFollowerIdAndFollowingId(followerId, followingId);

        if (alreadyFollowing) {
            userFollowRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
            userRepository.decrementFollowingCount(followerId);
            userRepository.decrementFollowersCount(followingId);
            return false; // unfollowed
        }

        UserFollowEntity follow = new UserFollowEntity();
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);

        userFollowRepository.save(follow);
        userRepository.incrementFollowingCount(followerId);
        userRepository.incrementFollowersCount(followingId);
        return true; // followed
    }
}
