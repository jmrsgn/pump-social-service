package com.johnmartin.social.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(String title, @NotBlank(message = "Post description is required") String description) {
}
