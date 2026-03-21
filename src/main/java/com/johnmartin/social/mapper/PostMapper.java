package com.johnmartin.social.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.dto.response.PostResponse;
import com.johnmartin.social.entities.PostEntity;
import com.johnmartin.social.entities.UserEntity;

public class PostMapper {

    private PostMapper() {
    }

    public static PostResponse toResponse(PostEntity post, List<CommentResponse> comments, UserEntity socialUser) {
        return new PostResponse(post.getId(),
                                post.getTitle(),
                                post.getDescription(),
                                socialUser.getFirstName() + " " + socialUser.getLastName(),
                                socialUser.getProfileImageUrl(),
                                post.getLikesCount(),
                                post.getCommentsCount(),
                                post.getSharesCount(),
                                CollectionUtils.isEmpty(comments) ? new ArrayList<>()
                                                                  : comments.stream()
                                                                            .sorted(Comparator.comparing(CommentResponse::createdAt))
                                                                            .toList(),
                                post.getCreatedAt(),
                                post.getUpdatedAt(),
                                post.getLikedByUserIds(),
                                isLikedByCurrentUser(post, socialUser.getId()));
    }

    private static boolean isLikedByCurrentUser(PostEntity post, String currentUserId) {
        return post.getLikedByUserIds() != null && post.getLikedByUserIds().contains(currentUserId);
    }
}
