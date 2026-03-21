package com.johnmartin.social.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record PostResponse(String id,
                           String title,
                           String description,
                           String author,
                           String authorProfileImageUrl,
                           int likesCount,
                           int commentsCount,
                           int sharesCount,
                           List<CommentResponse> comments,
                           Instant createdAt,
                           Instant updatedAt,
                           Set<String> likedByUserIds,
                           boolean isLikedByCurrentUser) {
}
