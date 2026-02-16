package com.johnmartin.social.repository.custom;

public interface CommentRepositoryCustom {

    void likeComment(String userId, String commentId);

    void unlikeComment(String userId, String commentId);
}
