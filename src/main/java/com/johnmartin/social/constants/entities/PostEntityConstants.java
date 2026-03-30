package com.johnmartin.social.constants.entities;

public class PostEntityConstants {

    private PostEntityConstants() {
    }

    public static final String TABLE_NAME = "posts";
    public static final String COLUMN_LIKES_COUNT = "likesCount";
    public static final String COLUMN_COMMENTS_COUNT = "commentsCount";

    public static class PostLike {
        public static final String TABLE_NAME = "posts_like";

        public static final String USER_POST_UNIQUE_INDEX = "user_post_unique";
        public static final String USER_POST_UNIQUE_DEF = "{'userId': 1, 'postId': 1}";
    }
}
