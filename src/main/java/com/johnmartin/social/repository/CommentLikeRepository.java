package com.johnmartin.social.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.social.entities.CommentLikeEntity;

@Repository
public interface CommentLikeRepository extends MongoRepository<CommentLikeEntity, String> {

    boolean existsByUserIdAndCommentId(String userId, String commentId);

    List<CommentLikeEntity> findByUserIdAndCommentIdIn(String userId, List<String> commentIds);

    void deleteByUserIdAndCommentId(String userId, String commentId);

    void deleteByCommentId(String commentId);

}
