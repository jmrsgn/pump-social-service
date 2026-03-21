package com.johnmartin.social.controller.internal;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.dto.request.CreateUserRequest;
import com.johnmartin.social.dto.response.Result;
import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.entities.UserEntity;
import com.johnmartin.social.mapper.UserMapper;
import com.johnmartin.social.service.UserService;
import com.johnmartin.social.utilities.LoggerUtility;
import com.johnmartin.social.utils.ApiErrorUtils;

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
    public ResponseEntity<Result<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        Optional<UserEntity> userOpt = userService.createUser(request);
        if (userOpt.isEmpty()) {
            LoggerUtility.d(clazz, "User creation failed");
            return ApiErrorUtils.createBadRequestErrorResponse(ApiErrorMessages.User.USER_CREATION_FAILED);
        }

        UserEntity userEntity = userOpt.get();
        LoggerUtility.d(clazz, String.format("userEntity: [%s]", userEntity));
        return ResponseEntity.ok(Result.success(UserMapper.toResponse(userEntity)));
    }
}
