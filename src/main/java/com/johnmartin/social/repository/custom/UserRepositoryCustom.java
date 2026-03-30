package com.johnmartin.social.repository.custom;

public interface UserRepositoryCustom {

    void incrementFollowingCount(String userId);

    void decrementFollowingCount(String userId);

    void incrementFollowersCount(String userId);

    void decrementFollowersCount(String userId);
}
