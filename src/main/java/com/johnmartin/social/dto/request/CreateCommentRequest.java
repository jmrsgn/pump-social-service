package com.johnmartin.social.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(String userId, @NotBlank(message = "Comment is required") String comment) {
}
