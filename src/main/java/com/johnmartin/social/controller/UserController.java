package com.johnmartin.social.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.dto.response.common.Result;
import com.johnmartin.social.service.UserService;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiConstants.Path.API_USER)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(ApiConstants.Path.FOLLOW)
    public ResponseEntity<Result<UserResponse>> followUser(@PathVariable(ApiConstants.Params.USER_ID) @NotBlank(message = "User ID is required") String userId) {
        UserResponse followedUser = userService.followUser(userId);
        return ResponseEntity.ok(Result.success(followedUser));
    }
}
