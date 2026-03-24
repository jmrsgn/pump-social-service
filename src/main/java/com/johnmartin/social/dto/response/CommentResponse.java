package com.johnmartin.social.dto.response;

import java.time.Instant;
import java.util.Set;

public record CommentResponse(String id,
                              String comment,
                              String postId,
                              String author,
                              String authorProfileImageUrl,
                              int likesCount,
                              int repliesCount,
                              Instant createdAt,
                              Instant updatedAt,
                              Set<String> likedByUserIds,
                              boolean isLikedByCurrentUser) {
}
