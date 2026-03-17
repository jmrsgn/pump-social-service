package com.johnmartin.social.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private String id;
    private String title;
    private String description;

    // Store user basic info for faster frontend rendering
    private String author;
    private String authorProfileImageUrl;

    private int likesCount;
    private int commentsCount;
    private int sharesCount;

    private List<CommentResponse> comments;

    private Instant createdAt;
    private Instant updatedAt;

    private Set<String> likedByUserIds;
    private boolean isLikedByCurrentUser;

    public PostResponse withId(String id) {
        setId(id);
        return this;
    }

    public PostResponse withTitle(String title) {
        setTitle(title);
        return this;
    }

    public PostResponse withDescription(String description) {
        setDescription(description);
        return this;
    }

    public PostResponse withAuthor(String author) {
        setAuthor(author);
        return this;
    }

    public PostResponse withAuthorProfileImageUrl(String authorProfileImageUrl) {
        setAuthorProfileImageUrl(authorProfileImageUrl);
        return this;
    }

    public PostResponse withLikesCount(int likesCount) {
        setLikesCount(likesCount);
        return this;
    }

    public PostResponse withCommentsCount(int commentsCount) {
        setCommentsCount(commentsCount);
        return this;
    }

    public PostResponse withSharesCount(int sharesCount) {
        setSharesCount(sharesCount);
        return this;
    }

    public PostResponse withComments(List<CommentResponse> comments) {
        setComments(comments);
        return this;
    }

    public PostResponse withCreatedAt(Instant createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public PostResponse withUpdatedAt(Instant updatedAt) {
        setUpdatedAt(updatedAt);
        return this;
    }

    public PostResponse withLikedByUserIds(Set<String> likedByUserIds) {
        setLikedByUserIds(likedByUserIds);
        return this;
    }

    public PostResponse withLikedByCurrentUser(boolean isLikedByCurrentUser) {
        setLikedByCurrentUser(isLikedByCurrentUser);
        return this;
    }

    @Override
    public String toString() {
        return "PostResponse{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='" + description
               + '\'' + ", author='" + author + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
               + ", likesCount=" + likesCount + ", commentsCount=" + commentsCount + ", sharesCount=" + sharesCount
               + '}';
    }
}
