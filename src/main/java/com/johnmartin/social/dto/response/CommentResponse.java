package com.johnmartin.social.dto.response;

import java.time.Instant;
import java.util.Set;

public class CommentResponse {
    private String id;
    private String comment;

    private String authorId;
    private String postId;

    // Store user basic info for faster frontend rendering
    private String author;
    private String authorProfileImageUrl;

    private Instant createdAt;
    private Instant updatedAt;

    private int likesCount;
    private int repliesCount;

    private Set<String> likedByUserIds;

    private boolean isLikedByCurrentUser;

    public CommentResponse(String id,
                           String comment,
                           String authorId,
                           String postId,
                           String author,
                           String authorProfileImageUrl,
                           Instant createdAt,
                           Instant updatedAt,
                           int likesCount,
                           int repliesCount,
                           Set<String> likedByUserIds,
                           boolean isLikedByCurrentUser) {
        this.id = id;
        this.comment = comment;
        this.authorId = authorId;
        this.postId = postId;
        this.author = author;
        this.authorProfileImageUrl = authorProfileImageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likesCount = likesCount;
        this.repliesCount = repliesCount;
        this.likedByUserIds = likedByUserIds;
        this.isLikedByCurrentUser = isLikedByCurrentUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorProfileImageUrl() {
        return authorProfileImageUrl;
    }

    public void setAuthorProfileImageUrl(String authorProfileImageUrl) {
        this.authorProfileImageUrl = authorProfileImageUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    public Set<String> getLikedByUserIds() {
        return likedByUserIds;
    }

    public void setLikedByUserIds(Set<String> likedByUserIds) {
        this.likedByUserIds = likedByUserIds;
    }

    public boolean isLikedByCurrentUser() {
        return isLikedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        isLikedByCurrentUser = likedByCurrentUser;
    }

    @Override
    public String toString() {
        return "CommentResponse{" + "id='" + id + '\'' + ", comment='" + comment + '\'' + ", postId='" + postId + '\''
               + ", author='" + author + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
               + ", likesCount=" + likesCount + ", repliesCount=" + repliesCount + '}';
    }
}
