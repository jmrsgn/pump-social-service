package com.johnmartin.social.mapper;

import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.entities.CommentEntity;
import com.johnmartin.social.entities.UserEntity;

public class CommentMapper {

    private CommentMapper() {
    }

    public static CommentResponse toResponse(CommentEntity comment,
                                             UserEntity commentAuthor,
                                             boolean isLikedByCurrentUser) {
        return new CommentResponse(comment.getId(),
                                   comment.getComment(),
                                   comment.getPostId(),
                                   comment.getParentCommentId(),
                                   commentAuthor.getFirstName() + " " + commentAuthor.getLastName(),
                                   commentAuthor.getProfileImageUrl(),
                                   comment.getLikesCount(),
                                   comment.getRepliesCount(),
                                   comment.getCreatedAt(),
                                   comment.getUpdatedAt(),
                                   isLikedByCurrentUser);
    }
}
