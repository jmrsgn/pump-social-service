package com.johnmartin.social.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class PostResponse {
    private String id;
    private String title;
    private String description;

    private String authorId;

    // Store user basic info for faster frontend rendering
    private String author;
    private String authorProfileImageUrl;

    private Instant createdAt;
    private Instant updatedAt;

    private List<CommentResponse> comments;

    private int likesCount;
    private int commentsCount;
    private int sharesCount;

    private Set<String> likedByUserIds;

    private boolean isLikedByCurrentUser;

    public PostResponse(String id,
                        String title,
                        String description,
                        String authorId,
                        String author,
                        String authorProfileImageUrl,
                        Instant createdAt,
                        Instant updatedAt,
                        List<CommentResponse> comments,
                        int likesCount,
                        int commentsCount,
                        int sharesCount,
                        Set<String> likedByUserIds,
                        boolean isLikedByCurrentUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.author = author;
        this.authorProfileImageUrl = authorProfileImageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.sharesCount = sharesCount;
        this.likedByUserIds = likedByUserIds;
        this.isLikedByCurrentUser = isLikedByCurrentUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getSharesCount() {
        return sharesCount;
    }

    public void setSharesCount(int sharesCount) {
        this.sharesCount = sharesCount;
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
        return "PostResponse{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='" + description
               + '\'' + ", author='" + author + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
               + ", likesCount=" + likesCount + ", commentsCount=" + commentsCount + ", sharesCount=" + sharesCount
               + '}';
    }
}
