package com.johnmartin.social.dto.request;

import com.johnmartin.social.constants.error.ValidationErrorConstants;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(String title,
                                @NotBlank(message = ValidationErrorConstants.POST_DESCRIPTION_IS_REQUIRED) String description) {
}
