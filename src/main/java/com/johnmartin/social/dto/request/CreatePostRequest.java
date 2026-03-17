package com.johnmartin.social.dto.request;

import com.johnmartin.social.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {

    private String title;

    @NotBlank(message = ApiErrorMessages.Post.POST_DESCRIPTION_IS_REQUIRED)
    private String description;
}
