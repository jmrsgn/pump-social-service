package com.johnmartin.social.dto.request;

import com.johnmartin.social.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(@NotBlank(message = ApiErrorMessages.User.USER_ID_IS_MISSING_IN_REQUEST) String id,
                                @NotBlank(message = ApiErrorMessages.User.FIRST_NAME_IS_REQUIRED) String firstName,
                                @NotBlank(message = ApiErrorMessages.User.LAST_NAME_IS_REQUIRED) String lastName,
                                @NotBlank(message = ApiErrorMessages.User.EMAIL_IS_REQUIRED) String email) {
}
