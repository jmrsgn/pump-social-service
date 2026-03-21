package com.johnmartin.social.dto.response;

import java.time.Instant;

public record ApiErrorResponse(int status, String error, String message, Instant timestamp) {

    public ApiErrorResponse(int status, String error, String message) {
        this(status, error, message, Instant.now());
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" + "status=" + status + ", error='" + error + '\'' + ", message='" + message + '\''
               + ", timestamp=" + timestamp + '}';
    }
}
