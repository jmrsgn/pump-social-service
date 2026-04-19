package com.johnmartin.social.constants.entities;

public final class CommentEntityConstants {

    private CommentEntityConstants() {
    }

    public static final String TABLE_NAME = "comments";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String COLUMN_POST_ID = "post_id";
    public static final String COLUMN_LIKES_COUNT = "likes_count";
    public static final String COLUMN_REPLIES_COUNT = "replies_count";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static final class CommentLike {

        private CommentLike() {
        }

        public static final String TABLE_NAME = "comment_likes";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_COMMENT_ID = "comment_id";

        public static final String INDEX_USER_COMMENT_UNIQUE = "user_comment_unique";
        /**
         * Defines a compound index on user_id and comment_id.
         *
         * This ensures that the combination of (user_id, comment_id) is unique, preventing duplicate interactions such
         * as multiple likes from the same user on the same comment.
         *
         * The value `1` indicates ascending index order, which is required by MongoDB but does not affect uniqueness in
         * this context.
         */
        public static final String DEF_USER_COMMENT_UNIQUE = "{'user_id': 1, 'comment_id': 1}";
    }
}
