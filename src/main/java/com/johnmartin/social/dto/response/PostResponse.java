package com.johnmartin.social.dto.response;

import java.time.Instant;
import java.util.List;

public record PostResponse(String id,
                           String title,
                           String description,

                           String author,
                           String authorProfileImageUrl,

                           String mediaUrl,
                           String mediaType,

                           int likesCount,
                           int commentsCount,
                           int sharesCount,

                           List<CommentResponse> comments,

                           Instant createdAt,
                           Instant updatedAt,

                           boolean isLikedByCurrentUser) {
}
