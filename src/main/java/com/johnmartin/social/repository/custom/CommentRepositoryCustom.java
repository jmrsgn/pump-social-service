package com.johnmartin.social.repository.custom;

public interface CommentRepositoryCustom {

    void incrementLikesCount(String commentId);

    void decrementLikesCount(String commentId);
}
