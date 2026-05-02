package com.johnmartin.social.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.johnmartin.social.entities.PostLikeEntity;
import com.johnmartin.social.repository.PostLikeRepository;
import com.johnmartin.social.repository.PostRepository;
import com.johnmartin.social.utilities.LoggerUtility;
import com.johnmartin.social.utils.ExtractorUtils;

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

    /**
     * Store entity that indicates a liked post, catch exception if has duplicate
     * 
     * @param postId
     *            - Post ID
     * @param userId
     *            - User ID
     * @return boolean
     */
    @Transactional
    public boolean toggleLike(String postId, String userId) {
        LoggerUtility.d(clazz, String.format("Execute method: [toggleLike] postId: [%s] userId: [%s]", postId, userId));

        boolean alreadyLiked = isPostLikedByUser(postId, userId);

        if (alreadyLiked) {
            postLikeRepository.deleteByUserIdAndPostId(userId, postId);
            postRepository.decrementLikesCount(postId);
            return false; // unliked
        }

        PostLikeEntity like = new PostLikeEntity();
        like.setPostId(postId);
        like.setUserId(userId);

        postLikeRepository.save(like);
        postRepository.incrementLikesCount(postId);

        return true; // liked
    }

    public Set<String> getLikedPostIds(List<String> postIds, String userId) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [getLikedPostIds]  postIds: [%s] userId: [%s]",
                                      postIds,
                                      userId));

        List<PostLikeEntity> likedPostIds = postLikeRepository.findByUserIdAndPostIdIn(userId, postIds);
        LoggerUtility.d(clazz, String.format("likedPostIds size: [%s]", likedPostIds.size()));
        return ExtractorUtils.extractToSet(likedPostIds, PostLikeEntity::getPostId);
    }

    public boolean isPostLikedByUser(String postId, String userId) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [isPostLikedByUser] postId: [%s] userId: [%s]", postId, userId));
        return postLikeRepository.existsByUserIdAndPostId(userId, postId);
    }

    public void deleteByPostId(String postId) {
        LoggerUtility.d(clazz, String.format("Execute method: [deleteByPostId] postId: [%s]", postId));
        postLikeRepository.deleteByPostId(postId);
    }
}
