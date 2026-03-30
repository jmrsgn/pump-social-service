package com.johnmartin.social.entities;

import java.time.Instant;

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

    private int likesCount;
    private int repliesCount;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @Override
    public String toString() {
        return "CommentEntity{" + "id='" + id + '\'' + ", comment='" + comment + '\'' + ", authorId='" + authorId + '\''
               + ", postId='" + postId + '\'' + ", likesCount=" + likesCount + ", repliesCount=" + repliesCount
               + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
