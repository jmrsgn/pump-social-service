package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.johnmartin.social.constants.entities.CommentEntityConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = CommentEntityConstants.CommentLike.TABLE_NAME)
@CompoundIndex(name = CommentEntityConstants.CommentLike.INDEX_USER_COMMENT_UNIQUE, def = CommentEntityConstants.CommentLike.DEF_USER_COMMENT_UNIQUE, unique = true)
public class CommentLikeEntity {

    @Id
    private String id;

    @Field(name = CommentEntityConstants.CommentLike.COLUMN_USER_ID)
    private String userId;
    @Field(name = CommentEntityConstants.CommentLike.COLUMN_COMMENT_ID)
    private String commentId;

    @CreatedDate
    @Field(name = CommentEntityConstants.COLUMN_CREATED_AT)
    private Instant createdAt;
    @LastModifiedDate
    @Field(name = CommentEntityConstants.COLUMN_UPDATED_AT)
    private Instant updatedAt;
}
