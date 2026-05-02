package com.johnmartin.social.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.johnmartin.social.entities.CommentLikeEntity;
import com.johnmartin.social.repository.CommentLikeRepository;
import com.johnmartin.social.repository.CommentRepository;
import com.johnmartin.social.utilities.LoggerUtility;
import com.johnmartin.social.utils.ExtractorUtils;

import jakarta.transaction.Transactional;

@Service
public class CommentLikeService {

    private static final Class<CommentLikeService> clazz = CommentLikeService.class;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    public CommentLikeService(CommentLikeRepository commentLikeRepository, CommentRepository commentRepository) {
        this.commentLikeRepository = commentLikeRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public boolean toggleLike(String commentId, String userId) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [toggleLike] commentId: [%s] userId: [%s]", commentId, userId));

        boolean alreadyLiked = isCommentLikedByUser(commentId, userId);

        if (alreadyLiked) {
            commentLikeRepository.deleteByUserIdAndCommentId(userId, commentId);
            commentRepository.decrementLikesCount(commentId);
            return false; // unliked
        }

        CommentLikeEntity like = new CommentLikeEntity();
        like.setCommentId(commentId);
        like.setUserId(userId);

        commentLikeRepository.save(like);
        commentRepository.incrementLikesCount(commentId);

        return true; // liked
    }

    public Set<String> getLikedCommentIds(List<String> commentIds, String userId) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [getLikedCommentIds]  commentIds: [%s] userId: [%s]",
                                      commentIds,
                                      userId));
        List<CommentLikeEntity> likedCommentIds = commentLikeRepository.findByUserIdAndCommentIdIn(userId, commentIds);
        LoggerUtility.d(clazz, String.format("likedCommentIds size: [%s]", likedCommentIds.size()));
        return ExtractorUtils.extractToSet(likedCommentIds, CommentLikeEntity::getCommentId);
    }

    public boolean isCommentLikedByUser(String commentId, String userId) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [isCommentLikedByUser] commentId: [%s] userId: [%s]",
                                      commentId,
                                      userId));
        return commentLikeRepository.existsByUserIdAndCommentId(userId, commentId);
    }

    public void deleteByCommentId(String commentId) {
        LoggerUtility.d(clazz, String.format("Execute method: [deleteByPostId] commentId: [%s]", commentId));
        commentLikeRepository.deleteByCommentId(commentId);
    }
}
