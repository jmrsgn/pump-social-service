package com.johnmartin.social.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.social.entities.CommentEntity;
import com.johnmartin.social.repository.custom.CommentRepositoryCustom;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String>, CommentRepositoryCustom {
    List<CommentEntity> findByPostIdOrderByCreatedAtDesc(String postId, PageRequest pageRequest);

    List<CommentEntity> findByPostIdAndCreatedAtLessThanOrderByCreatedAtDesc(String postId,
                                                                             Instant createdAt,
                                                                             Pageable pageable);

    void deleteByPostId(String postId);
}
