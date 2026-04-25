package com.johnmartin.social.constants.api;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final int RETRIES_COUNT = 5;

    public static final String API_BASE_V1 = "/api/v1";
    public static final String API_BASE_V1_INTERNAL = "/api/v1/internal";

    public static final class Path {

        private Path() {
        }

        public static final String ACTUATOR = "/actuator";
        public static final String HEALTH = "/health";

        // User
        public static final String API_USER = API_BASE_V1 + "/user";
        public static final String USER_INFO = "/{userId}";
        public static final String FOLLOW = USER_INFO + "/follow";

        // Post
        public static final String API_POST = API_BASE_V1 + "/post";
        public static final String POST_INFO = "/{postId}";
        public static final String POST_LIKE = POST_INFO + "/like";

        // Comment
        public static final String API_COMMENT = API_POST + "/{postId}/comment";
        public static final String COMMENT_INFO = "/{commentId}";
        public static final String COMMENT_LIKE = COMMENT_INFO + "/like";
        public static final String COMMENT_REPLIES = COMMENT_INFO + "/replies";
    }

    public static final class InternalPath {

        private InternalPath() {
        }

        public static final String API_USER_INTERNAL = API_BASE_V1_INTERNAL + "/user";
        public static final String CREATE_USER = "/create";
        public static final String GET_USER = "/{userId}";
    }

    public static final class Params {

        private Params() {
        }

        public static final String POST_ID = "postId";
        public static final String COMMENT_ID = "commentId";
        public static final String USER_ID = "userId";
    }

    public static final class Error {

        private Error() {
        }

        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String NOT_FOUND = "Not found";
        public static final String BAD_REQUEST = "Bad Request";
        public static final String CONFLICT = "Conflict";
        public static final String FORBIDDEN = "Forbidden";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }

    public static final class PumpAuthService {

        private PumpAuthService() {
        }

        // public static final String URL = "http://pump-auth-service:8080";
        public static final String URL = "http://localhost:8081";
        public static final String API_VALIDATE = API_BASE_V1_INTERNAL + "/auth/validate";
    }
}
