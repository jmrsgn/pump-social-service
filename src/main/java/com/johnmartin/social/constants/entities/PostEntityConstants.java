package com.johnmartin.social.constants.entities;

public class PostEntityConstants {

    private PostEntityConstants() {
    }

    public static final String TABLE_NAME = "posts";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_MEDIA_URL = "media_url";
    public static final String COLUMN_MEDIA_TYPE = "media_type";
    public static final String COLUMN_LIKES_COUNT = "likes_count";
    public static final String COLUMN_COMMENTS_COUNT = "comments_count";
    public static final String COLUMN_SHARES_COUNT = "shares_count";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static class PostLike {
        public static final String TABLE_NAME = "post_likes";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_POST_ID = "post_id";

        public static final String INDEX_USER_POST_UNIQUE = "user_post_unique";
        /**
         * Defines a compound index on user_id and post_id.
         *
         * This ensures that the combination of (user_id, post_id) is unique, preventing duplicate interactions such as
         * multiple likes from the same user on the same post.
         *
         * The value `1` indicates ascending index order, which is required by MongoDB but does not affect uniqueness in
         * this context.
         */
        public static final String DEF_USER_POST_UNIQUE = "{'user_id': 1, 'post_id': 1}";
    }
}
