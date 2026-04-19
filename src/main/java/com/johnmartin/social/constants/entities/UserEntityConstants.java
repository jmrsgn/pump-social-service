package com.johnmartin.social.constants.entities;

public final class UserEntityConstants {

    private UserEntityConstants() {
    }

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PROFILE_IMAGE_URL = "profile_image_url";
    public static final String COLUMN_BIO = "bio";
    public static final String COLUMN_FOLLOWERS_COUNT = "followers_count";
    public static final String COLUMN_FOLLOWING_COUNT = "following_count";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static final class UserFollow {

        private UserFollow() {
        }

        public static final String TABLE_NAME = "user_follows";
        public static final String COLUMN_FOLLOWER_ID = "follower_id";
        public static final String COLUMN_FOLLOWING_ID = "following_id";

        public static final String INDEX_USER_FOLLOW_UNIQUE = "user_follow_unique";
        /**
         * Defines a compound unique index on follower_id and following_id.
         *
         * This ensures that each user (follower_id) can follow another user (following_id) only once, preventing
         * duplicate follow relationships in the collection.
         *
         * The value `1` specifies ascending index order for both fields, which is required by MongoDB syntax but does
         * not affect the uniqueness constraint.
         */
        public static final String DEF_USER_FOLLOW_UNIQUE = "{'follower_id': 1, 'following_id': 1}";
    }
}
