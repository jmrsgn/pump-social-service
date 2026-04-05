package com.johnmartin.social.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostRequest(String title, @NotBlank(message = "Post description is required") String description) {
}
