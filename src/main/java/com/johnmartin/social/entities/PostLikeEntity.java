package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.johnmartin.social.constants.entities.PostEntityConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = PostEntityConstants.PostLike.TABLE_NAME)
@CompoundIndex(name = PostEntityConstants.PostLike.INDEX_USER_POST_UNIQUE, def = PostEntityConstants.PostLike.DEF_USER_POST_UNIQUE, unique = true)
public class PostLikeEntity {

    @Id
    private String id;

    @Field(name = PostEntityConstants.PostLike.COLUMN_USER_ID)
    private String userId;
    @Field(name = PostEntityConstants.PostLike.COLUMN_POST_ID)
    private String postId;

    @CreatedDate
    @Field(name = PostEntityConstants.COLUMN_CREATED_AT)
    private Instant createdAt;
    @LastModifiedDate
    @Field(name = PostEntityConstants.COLUMN_UPDATED_AT)
    private Instant updatedAt;
}
