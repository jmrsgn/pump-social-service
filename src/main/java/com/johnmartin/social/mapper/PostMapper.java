package com.johnmartin.social.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.dto.response.PostResponse;
import com.johnmartin.social.entities.PostEntity;

public class PostMapper {
    public static PostResponse toResponse(PostEntity post, List<CommentResponse> comments, String currentUserId) {
        return new PostResponse(post.getId(),
                                post.getTitle(),
                                post.getDescription(),
                                post.getAuthorId(),
                                post.getAuthor(),
                                post.getAuthorProfileImageUrl(),
                                post.getCreatedAt(),
                                post.getUpdatedAt(),

                                // Comments are sorted from oldest to newest
                                CollectionUtils.isEmpty(comments) ? new ArrayList<>()
                                                                  : comments.stream()
                                                                            .sorted(Comparator.comparing(CommentResponse::getCreatedAt))
                                                                            .toList(),
                                post.getLikesCount(),
                                post.getCommentsCount(),
                                post.getSharesCount(),
                                post.getLikedByUserIds(),
                                isLikedByCurrentUser(post, currentUserId));
    }

    private static boolean isLikedByCurrentUser(PostEntity post, String currentUserId) {
        return post.getLikedByUserIds() != null && post.getLikedByUserIds().contains(currentUserId);
    }
}
