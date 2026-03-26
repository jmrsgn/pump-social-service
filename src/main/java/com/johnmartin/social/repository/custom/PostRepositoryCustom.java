package com.johnmartin.social.repository.custom;

public interface PostRepositoryCustom {

    void incrementCommentsCount(String postId);

    void decrementCommentsCount(String postId);

    void incrementLikesCount(String postId);

    void decrementLikesCount(String postId);
}
