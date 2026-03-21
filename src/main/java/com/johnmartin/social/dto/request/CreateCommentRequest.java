package com.johnmartin.social.dto.request;

import com.johnmartin.social.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(String userId,
                                   @NotBlank(message = ApiErrorMessages.Comment.COMMENT_IS_REQUIRED) String comment) {
}
