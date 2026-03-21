package com.johnmartin.social.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.johnmartin.social.constants.entities.CommentEntityConstants;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    @Override
    public String toString() {
        return "CommentEntity{" + "id='" + id + '\'' + ", comment='" + comment + '\'' + ", authorId='" + authorId + '\''
               + ", postId='" + postId + '\'' + ", author='" + author + '\'' + ", authorProfileImageUrl='"
               + authorProfileImageUrl + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
               + ", likesCount=" + likesCount + ", repliesCount=" + repliesCount + ", likedByUserIds=" + likedByUserIds
               + '}';
    }
}
