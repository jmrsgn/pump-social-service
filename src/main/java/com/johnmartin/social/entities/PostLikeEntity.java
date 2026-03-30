package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.johnmartin.social.constants.entities.PostEntityConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = PostEntityConstants.PostLike.TABLE_NAME)
@CompoundIndex(name = PostEntityConstants.PostLike.USER_POST_UNIQUE_INDEX, def = PostEntityConstants.PostLike.USER_POST_UNIQUE_DEF, unique = true)
public class PostLikeEntity {

    @Id
    private String id;

    private String userId;
    private String postId;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
