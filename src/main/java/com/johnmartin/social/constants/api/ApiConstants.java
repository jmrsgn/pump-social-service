package com.johnmartin.social.constants.api;

public class ApiConstants {

    private ApiConstants() {
    }

    public static final int RETRIES_COUNT = 5;

    public static final String API_BASE_V1 = "/api/v1";

    public static class Path {
        public static final String ACTUATOR = "/actuator";
        public static final String HEALTH = "/health";

        // User
        public static final String API_USER = API_BASE_V1 + "/user";
        public static final String PROFILE = "/profile";

        // Post
        public static final String API_POST = API_BASE_V1 + "/post";
        public static final String POST_INFO = "/{postId}";
        public static final String POST_LIKE = POST_INFO + "/like";

        // Comment
        public static final String API_COMMENT = API_POST + "/{postId}/comment";
        public static final String COMMENT_INFO = "/{commentId}";
        public static final String COMMENT_LIKE = COMMENT_INFO + "/like";
    }

    public static class Params {
        public static final String POST_ID = "postId";
        public static final String COMMENT_ID = "commentId";
    }

    public static class Error {
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String NOT_FOUND = "Not found";
        public static final String BAD_REQUEST = "Bad Request";
        public static final String CONFLICT = "Conflict";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }

    public static class PumpAuthService {
        public static final String URL = "http://pump-auth-service:8080";
        public static final String API_VALIDATE = API_BASE_V1 + "/internal/auth/validate";
    }
}
