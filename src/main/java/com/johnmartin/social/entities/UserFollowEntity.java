package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.johnmartin.social.constants.entities.UserEntityConstants;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = UserEntityConstants.UserFollow.TABLE_NAME)
@CompoundIndex(name = UserEntityConstants.UserFollow.INDEX_USER_FOLLOW_UNIQUE, def = UserEntityConstants.UserFollow.DEF_USER_FOLLOW_UNIQUE, unique = true)
public class UserFollowEntity {

    @Id
    private String id;

    @Field(name = UserEntityConstants.UserFollow.COLUMN_FOLLOWER_ID)
    private String followerId;
    @Field(name = UserEntityConstants.UserFollow.COLUMN_FOLLOWING_ID)
    private String followingId;

    @CreatedDate
    @Field(name = UserEntityConstants.COLUMN_CREATED_AT)
    private Instant createdAt;
    @LastModifiedDate
    @Field(name = UserEntityConstants.COLUMN_UPDATED_AT)
    private Instant updatedAt;
}
