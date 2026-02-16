package com.johnmartin.social.dto.response;

import java.util.Optional;

/**
 * A generic Result class that represents either a success with data or a failure with an error.
 *
 * @param <T>
 *            Type of the success data
 */
public class Result<T> {
    private final T data;
    private final ApiErrorResponse apiErrorResponse;

    private Result(T data, ApiErrorResponse apiErrorResponse) {
        this.data = data;
        this.apiErrorResponse = apiErrorResponse;
    }

    /** Factory method for success */
    public static <T> Result<T> success(T data) {
        return new Result<>(data, null);
    }

    /** Factory method for failure */
    public static <T> Result<T> failure(ApiErrorResponse apiErrorResponse) {
        return new Result<>(null, apiErrorResponse);
    }

    /** Check if result is success */
    public boolean isSuccess() {
        return data != null && apiErrorResponse == null;
    }

    /** Check if result is failure */
    public boolean isFailure() {
        return apiErrorResponse != null;
    }

    /** Get data wrapped in Optional */
    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }

    public ApiErrorResponse getError() {
        return apiErrorResponse;
    }
}
