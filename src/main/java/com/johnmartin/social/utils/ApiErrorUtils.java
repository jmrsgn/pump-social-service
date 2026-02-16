package com.johnmartin.social.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.dto.response.ApiErrorResponse;
import com.johnmartin.social.dto.response.Result;

public class ApiErrorUtils {

    public static <T> ResponseEntity<Result<T>> createInternalServerErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                      ApiConstants.Error.INTERNAL_SERVER_ERROR,
                                                      message);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.failure(error));
    }

    public static <T> ResponseEntity<Result<T>> createNotFoundErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
                                                      ApiConstants.Error.NOT_FOUND,
                                                      message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.failure(error));
    }

    public static <T> ResponseEntity<Result<T>> createUnauthorizedErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                                                      ApiConstants.Error.UNAUTHORIZED,
                                                      StringUtils.defaultIfBlank(message,
                                                                                 ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.failure(error));
    }

    public static <T> ResponseEntity<Result<T>> createBadRequestErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                                      ApiConstants.Error.BAD_REQUEST,
                                                      message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.failure(error));
    }

    public static <T> ResponseEntity<Result<T>> createConflictErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.CONFLICT.value(),
                                                      ApiConstants.Error.CONFLICT,
                                                      message);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.failure(error));
    }
}
