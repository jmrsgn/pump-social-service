package com.johnmartin.social.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.johnmartin.social.constants.entities.CommentEntityConstants;

@Document(collection = CommentEntityConstants.TABLE_NAME)
public class CommentEntity {

    @Id
    private String id;
    private String comment;

    private String authorId;
    private String postId;

    // Store user basic info for faster frontend rendering
    private String author;
    private String authorProfileImageUrl;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    private int likesCount;
    private int repliesCount;

    private Set<String> likedByUserIds = new HashSet<>();

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

    @Override
    public String toString() {
        return "CommentEntity{" + "id='" + id + '\'' + ", comment='" + comment + '\'' + ", postId='" + postId + '\''
               + ", author='" + author + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
               + ", likesCount=" + likesCount + ", repliesCount=" + repliesCount + '}';
    }
}
