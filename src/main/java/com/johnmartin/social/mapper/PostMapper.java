package com.johnmartin.social.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.dto.response.PostResponse;
import com.johnmartin.social.entities.PostEntity;

public class PostMapper {
    public static PostResponse toResponse(PostEntity post, List<CommentResponse> comments, AuthUser authUser) {
        return new PostResponse().withId(post.getId())
                                 .withTitle(post.getTitle())
                                 .withDescription(post.getDescription())
                                 .withAuthor(authUser.getFirstName() + authUser.getLastName())
                                 .withCreatedAt(post.getCreatedAt())
                                 .withUpdatedAt(post.getUpdatedAt())
                                 .withComments(CollectionUtils.isEmpty(comments) ? new ArrayList<>()
                                                                                 : comments.stream()
                                                                                           .sorted(Comparator.comparing(CommentResponse::getCreatedAt))
                                                                                           .toList())
                                 .withLikesCount(post.getLikesCount())
                                 .withCommentsCount(post.getCommentsCount())
                                 .withSharesCount(post.getSharesCount())
                                 .withLikedByUserIds(post.getLikedByUserIds())
                                 .withLikedByCurrentUser(isLikedByCurrentUser(post, authUser.getId()));
    }

    private static boolean isLikedByCurrentUser(PostEntity post, String currentUserId) {
        return post.getLikedByUserIds() != null && post.getLikedByUserIds().contains(currentUserId);
    }
}
