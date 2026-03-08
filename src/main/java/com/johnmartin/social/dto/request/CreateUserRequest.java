package com.johnmartin.social.dto.request;

import com.johnmartin.social.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {

    @NotBlank(message = ApiErrorMessages.User.USER_ID_IS_MISSING_IN_REQUEST)
    private String id;

    @NotBlank(message = ApiErrorMessages.User.FIRST_NAME_IS_REQUIRED)
    private String firstName;

    @NotBlank(message = ApiErrorMessages.User.LAST_NAME_IS_REQUIRED)
    private String lastName;

    @NotBlank(message = ApiErrorMessages.User.EMAIL_IS_REQUIRED)
    private String email;

    @Override
    public String toString() {
        return "CreateUserRequest{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='"
               + lastName + '\'' + ", email='" + email + '\'' + '}';
    }
}
