package com.johnmartin.social.repository.custom;

public interface PostRepositoryCustom {

    void incrementCommentsCount(String postId);

    void decrementCommentsCount(String postId);

    void likePost(String userId, String postId);

    void unlikePost(String userId, String postId);
}
