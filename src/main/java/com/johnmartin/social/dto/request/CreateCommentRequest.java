package com.johnmartin.social.dto.request;

import com.johnmartin.social.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequest {

    @NotBlank(message = ApiErrorMessages.Comment.COMMENT_IS_REQUIRED)
    private String comment;

    public CreateCommentRequest(String comment, String userId) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
