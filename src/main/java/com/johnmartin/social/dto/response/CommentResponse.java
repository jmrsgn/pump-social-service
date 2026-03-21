package com.johnmartin.social.dto.response;

import java.time.Instant;
import java.util.Set;

public record CommentResponse(String id,
                              String comment,
                              String authorId,
                              String postId,
                              String author,
                              String authorProfileImageUrl,
                              Instant createdAt,
                              Instant updatedAt,
                              int likesCount,
                              int repliesCount,
                              Set<String> likedByUserIds,
                              boolean isLikedByCurrentUser) {
}
