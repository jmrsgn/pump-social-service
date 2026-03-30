package com.johnmartin.social.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.johnmartin.social.entities.UserFollowEntity;

public interface UserFollowRepository extends MongoRepository<UserFollowEntity, String> {

    boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);

    void deleteByFollowerIdAndFollowingId(String followerId, String followingId);

    List<UserFollowEntity> findByFollowerId(String followerId); // users I follow

    List<UserFollowEntity> findByFollowingId(String followingId); // users who follow me

    // 🔹 Feed optimization (only fetch IDs, not full documents)
    @Query(value = "{ 'followerId': ?0 }", fields = "{ 'followingId': 1, '_id': 0 }")
    List<UserFollowEntity> findFollowingIds(String followerId);

    long countByFollowerId(String followerId);

    long countByFollowingId(String followingId);
}
