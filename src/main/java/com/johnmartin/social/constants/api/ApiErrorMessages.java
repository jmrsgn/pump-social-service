package com.johnmartin.social.constants.api;

public class ApiErrorMessages {

    private ApiErrorMessages() {
    }

    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String MISSING_AUTH_HEADER = "Missing Authentication Header";

    public static class User {
        public static final String USER_IS_NOT_AUTHENTICATED = "User is not authenticated";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String INVALID_TOKEN = "Invalid token";
        public static final String EMAIL_IS_REQUIRED = "Email is required";
        public static final String PASSWORD_IS_REQUIRED = "Password is required";
        public static final String EMAIL_MUST_BE_VALID = "Email must be valid";
        public static final String YOU_ARE_NOT_ALLOWED_TO_ACCESS_THIS_RESOURCE = "You are not allowed to access this resource";
        public static final String YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION = "You are not authorized to perform this action.";
    }

    public static class Post {
        public static final String POST_NOT_FOUND = "Post not found";
        public static final String POST_DESCRIPTION_IS_REQUIRED = "Post description is required";
        public static final String POST_ID_IS_REQUIRED = "Post ID is required";
    }

    public static class Comment {
        public static final String COMMENT_IS_REQUIRED = "Comment is required";
        public static final String COMMENT_ID_IS_REQUIRED = "Comment ID is required";
        public static final String COMMENT_NOT_FOUND = "Comment not found";
        public static final String THE_COMMENT_DOES_NOT_BELONG_TO_THE_SPECIFIED_POST = "The comment does not belong to the specified post";
    }

}
