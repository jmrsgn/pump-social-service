package com.johnmartin.social.mapper;

import java.util.Collections;
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

    public static PostResponse toResponse(PostEntity post,
                                          List<CommentResponse> comments,
                                          UserEntity postAuthor,
                                          boolean isLikedByCurrentUser,
                                          boolean isOwnedByCurrentUser) {
        return new PostResponse(post.getId(),
                                post.getTitle(),
                                post.getDescription(),
                                postAuthor.getFirstName() + " " + postAuthor.getLastName(),
                                postAuthor.getProfileImageUrl(),
                                post.getMediaUrl(),
                                post.getMediaType() == null ? null : post.getMediaType().name(),
                                post.getLikesCount(),
                                post.getCommentsCount(),
                                post.getSharesCount(),
                                CollectionUtils.isEmpty(comments) ? Collections.emptyList()
                                                                  : comments.stream()
                                                                            .sorted(Comparator.comparing(CommentResponse::createdAt))
                                                                            .toList(),
                                post.getCreatedAt(),
                                post.getUpdatedAt(),
                                isLikedByCurrentUser,
                                isOwnedByCurrentUser);
    }
}
