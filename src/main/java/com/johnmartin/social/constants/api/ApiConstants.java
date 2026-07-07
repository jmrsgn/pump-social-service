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
        public static final String API_POSTS = API_BASE_V1 + "/posts";
        public static final String POST_INFO = "/{postId}";
        public static final String POST_LIKE = POST_INFO + "/like";

        // Comment
        public static final String API_COMMENTS = API_POSTS + "/{postId}/comments";
        public static final String COMMENT_INFO = "/{commentId}";
        public static final String COMMENT_LIKE = COMMENT_INFO + "/like";
        public static final String COMMENT_REPLIES = COMMENT_INFO + "/replies";

        // AI
        public static final String API_AI = API_BASE_V1 + "/ai";
        public static final String CAPTIONS = "/caption";
    }

    public static final class InternalPath {

        private InternalPath() {
        }

        public static final String API_SOCIAL_INTERNAL = API_BASE_V1_INTERNAL + "/social";
        public static final String API_USER_INTERNAL = API_SOCIAL_INTERNAL + "/users";
        public static final String CREATE_USER = "/create";
        public static final String SEARCH_USER = "/search";
        public static final String GET_USER = "/{userId}";
    }

    public static final class StaticResource {

        private StaticResource() {
        }

        public static final String UPLOADS = "/uploads";
    }

    public static final class Params {

        private Params() {
        }

        public static final String POST_ID = "postId";
        public static final String COMMENT_ID = "commentId";
        public static final String USER_ID = "userId";
        public static final String QUERY = "query";
    }

    public static final class HttpError {

        private HttpError() {
        }

        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String NOT_FOUND = "Not found";
        public static final String BAD_REQUEST = "Bad Request";
        public static final String CONFLICT = "Conflict";
        public static final String FORBIDDEN = "Forbidden";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }
}
