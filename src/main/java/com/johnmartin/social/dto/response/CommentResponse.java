package com.johnmartin.social.dto.response;

import java.time.Instant;

public record CommentResponse(String id,
                              String comment,
                              String postId,
                              String parentCommentId,

                              String author,
                              String authorProfileImageUrl,

                              int likesCount,
                              int repliesCount,

                              Instant createdAt,
                              Instant updatedAt,

                              boolean isLikedByCurrentUser) {
}
