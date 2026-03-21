package com.johnmartin.social.dto.request;

import com.johnmartin.social.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(String title,
                                @NotBlank(message = ApiErrorMessages.Post.POST_DESCRIPTION_IS_REQUIRED) String description) {

    @Override
    public String toString() {
        return "CreatePostRequest{" + "title='" + title + '\'' + ", description='" + description + '\'' + '}';
    }
}
