package com.johnmartin.social.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.johnmartin.social.constants.entities.PostEntityConstants;

@Document(collection = PostEntityConstants.TABLE_NAME)
public class PostEntity {

    @Id
    private String id;
    private String title;
    private String description;

    private String authorId;

    // Store user basic info for faster frontend rendering
    private String author;
    private String authorProfileImageUrl;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    private int likesCount;
    private int commentsCount;
    private int sharesCount;

    private Set<String> likedByUserIds = new HashSet<>();

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

    @Override
    public String toString() {
        return "PostEntity{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='" + description + '\''
               + ", author='" + author + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
               + ", likesCount=" + likesCount + ", commentsCount=" + commentsCount + ", sharesCount=" + sharesCount
               + '}';
    }
}
