package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.johnmartin.social.constants.entities.CommentEntityConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = CommentEntityConstants.CommentLike.TABLE_NAME)
@CompoundIndex(name = CommentEntityConstants.CommentLike.USER_COMMENT_UNIQUE_INDEX, def = CommentEntityConstants.CommentLike.USER_COMMENT_UNIQUE_DEF, unique = true)
public class CommentLikeEntity {

    @Id
    private String id;

    private String userId;
    private String commentId;

    private Instant createdAt;
    private Instant updatedAt;
}
