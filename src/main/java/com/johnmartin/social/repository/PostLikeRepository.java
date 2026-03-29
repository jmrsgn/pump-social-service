package com.johnmartin.social.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.social.entities.PostLikeEntity;

@Repository
public interface PostLikeRepository extends MongoRepository<PostLikeEntity, String> {

    boolean existsByUserIdAndPostId(String userId, String postId);

    List<PostLikeEntity> findByUserIdAndPostIdIn(String userId, List<String> postIds);

    void deleteByUserIdAndPostId(String userId, String postId);

    void deleteByPostId(String postId);
}
