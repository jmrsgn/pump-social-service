package com.johnmartin.social.constants.entities;

public class CommentEntityConstants {

    private CommentEntityConstants() {
    }

    public static final String TABLE_NAME = "comments";
    public static final String COLUMN_LIKES_COUNT = "likesCount";

    public static class CommentLike {
        public static final String TABLE_NAME = "comments_like";

        public static final String USER_COMMENT_UNIQUE_INDEX = "user_comment_unique";
        public static final String USER_COMMENT_UNIQUE_DEF = "{'userId': 1, 'commentId': 1}";
    }
}
