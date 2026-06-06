package com.johnmartin.social.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.error.ValidationErrorConstants;
import com.johnmartin.social.dto.response.UserResponse;
import com.johnmartin.social.dto.response.UserSummaryResponse;
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
    public ResponseEntity<Result<UserResponse>> followUser(@PathVariable(ApiConstants.Params.USER_ID) @NotBlank(message = ValidationErrorConstants.USER_ID_IS_REQUIRED) String userId) {
        UserResponse followedUser = userService.followUser(userId);
        return ResponseEntity.ok(Result.success(followedUser));
    }

    @GetMapping(ApiConstants.Path.SEARCH)
    public ResponseEntity<Result<List<UserSummaryResponse>>> searchUsers(@RequestParam String query) {
        List<UserSummaryResponse> users = userService.searchUsers(query);
        return ResponseEntity.ok(Result.success(users));
    }
}
