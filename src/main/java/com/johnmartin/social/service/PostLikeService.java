package com.johnmartin.social.service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.johnmartin.social.entities.PostEntity;
import com.johnmartin.social.entities.PostLikeEntity;
import com.johnmartin.social.repository.PostLikeRepository;
import com.johnmartin.social.repository.PostRepository;
import com.johnmartin.social.utilities.LoggerUtility;
import com.mongodb.DuplicateKeyException;

import jakarta.transaction.Transactional;

@Service
public class PostLikeService {

    private static final Class<PostLikeService> clazz = PostLikeService.class;

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public PostLikeService(PostLikeRepository postLikeRepository, PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public boolean toggleLike(String postId, String userId) {
        LoggerUtility.d(clazz, String.format("Execute method: [toggleLike] postId: [%s] userId: [%s]", postId, userId));
        try {
            PostLikeEntity like = new PostLikeEntity();
            like.setPostId(postId);
            like.setUserId(userId);
            like.setCreatedAt(Instant.now());

            postLikeRepository.save(like);
            postRepository.incrementLikesCount(postId);
            return true; // liked
        } catch (DuplicateKeyException ex) {
            postLikeRepository.deleteByUserIdAndPostId(userId, postId);
            postRepository.decrementLikesCount(postId);
            return false; // unliked
        }
    }

    public Set<String> getLikedPostIds(String userId, List<String> postIds) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [getLikedPostIds] userId: [%s] postIds: [%s]", userId, postIds));
        return postLikeRepository.findByUserIdAndPostIdIn(userId, postIds)
                                 .stream()
                                 .map(PostLikeEntity::getPostId)
                                 .collect(Collectors.toSet());
    }

    public boolean isPostLikedByUser(String postId, String userId) {
        return postLikeRepository.existsByUserIdAndPostId(userId, postId);
    }

    public void deleteByPostId(String postId) {
        LoggerUtility.d(clazz, String.format("Execute method: [deleteByPostId] postId: [%s]", postId));
        postLikeRepository.deleteByPostId(postLikeRepository);
    }
}
