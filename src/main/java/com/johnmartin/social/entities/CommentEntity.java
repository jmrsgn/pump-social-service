package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.johnmartin.social.constants.entities.CommentEntityConstants;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = CommentEntityConstants.TABLE_NAME)
public class CommentEntity {

    @Id
    private String id;
    @Field(name = CommentEntityConstants.COLUMN_COMMENT)
    private String comment;
    @Field(name = CommentEntityConstants.COLUMN_AUTHOR_ID)
    private String authorId;
    @Field(name = CommentEntityConstants.COLUMN_POST_ID)
    private String postId;
    @Field(name = CommentEntityConstants.COLUMN_PARENT_COMMENT_ID)
    private String parentCommentId; // nullable

    @Field(name = CommentEntityConstants.COLUMN_LIKES_COUNT)
    private int likesCount;
    @Field(name = CommentEntityConstants.COLUMN_REPLIES_COUNT)
    private int repliesCount;

    @CreatedDate
    @Field(name = CommentEntityConstants.COLUMN_CREATED_AT)
    private Instant createdAt;
    @LastModifiedDate
    @Field(name = CommentEntityConstants.COLUMN_UPDATED_AT)
    private Instant updatedAt;

    @Override
    public String toString() {
        return "CommentEntity{" + "id='" + id + '\'' + ", comment='" + comment + '\'' + ", authorId='" + authorId + '\''
               + ", postId='" + postId + '\'' + ", likesCount=" + likesCount + ", repliesCount=" + repliesCount
               + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
