package com.johnmartin.social.mapper;

import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.entities.CommentEntity;

public class CommentMapper {

    public static CommentResponse toResponse(CommentEntity comment, String currentUserId) {
        return new CommentResponse(comment.getId(),
                                   comment.getComment(),
                                   comment.getAuthorId(),
                                   comment.getPostId(),
                                   comment.getAuthor(),
                                   comment.getAuthorProfileImageUrl(),
                                   comment.getCreatedAt(),
                                   comment.getUpdatedAt(),
                                   comment.getLikesCount(),
                                   comment.getRepliesCount(),
                                   comment.getLikedByUserIds(),
                                   isLikedByCurrentUser(comment, currentUserId));
    }

    private static boolean isLikedByCurrentUser(CommentEntity comment, String currentUserId) {
        return comment.getLikedByUserIds() != null && comment.getLikedByUserIds().contains(currentUserId);
    }
}
