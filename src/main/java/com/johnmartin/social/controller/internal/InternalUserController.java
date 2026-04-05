package com.johnmartin.social.controller.internal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.dto.request.CreateUserRequest;
import com.johnmartin.social.entities.UserEntity;
import com.johnmartin.social.service.UserService;
import com.johnmartin.social.utilities.LoggerUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiConstants.InternalPath.API_USER_INTERNAL)
public class InternalUserController {

    private static final Class<InternalUserController> clazz = InternalUserController.class;

    private final UserService userService;

    public InternalUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(ApiConstants.InternalPath.CREATE_USER)
    public void createUser(@Valid @RequestBody CreateUserRequest request) {
        UserEntity socialUser = userService.createUser(request);
        if (socialUser == null) {
            LoggerUtility.d(clazz, "User creation failed");
            throw new RuntimeException("User creation failed");
        }

        LoggerUtility.d(clazz, String.format("socialUser: [%s]", socialUser));
    }
}
