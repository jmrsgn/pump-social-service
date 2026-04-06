package com.johnmartin.social.controller.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.dto.request.CreateUserRequest;
import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.dto.response.common.Result;
import com.johnmartin.social.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiConstants.InternalPath.API_USER_INTERNAL)
public class InternalUserController {

    private final UserService userService;

    public InternalUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(ApiConstants.InternalPath.CREATE_USER)
    public ResponseEntity<Result<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.ok(Result.success(user));
    }

    @GetMapping(ApiConstants.InternalPath.GET_USER)
    public ResponseEntity<Result<UserResponse>> getUser(@PathVariable(ApiConstants.Params.USER_ID) @NotBlank(message = "User ID is required") String userId) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(Result.success(user));
    }
}
