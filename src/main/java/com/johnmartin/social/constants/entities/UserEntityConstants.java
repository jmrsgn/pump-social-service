package com.johnmartin.social.constants.entities;

public class UserEntityConstants {

    private UserEntityConstants() {
    }

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_FOLLOWERS_COUNT = "followers_count";
    public static final String COLUMN_FOLLOWING_COUNT = "following_count";

    public static class UserFollow {
        public static final String TABLE_NAME = "user_follow";

        public static final String USER_FOLLOW_UNIQUE_INDEX = "user_follow_unique";
        public static final String USER_FOLLOW_UNIQUE_DEF = "{'followerId': 1, 'followingId': 1}";
    }
}
