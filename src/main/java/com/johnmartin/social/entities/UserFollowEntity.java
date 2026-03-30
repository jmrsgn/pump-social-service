package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.johnmartin.social.constants.entities.UserEntityConstants;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = UserEntityConstants.UserFollow.TABLE_NAME)
@CompoundIndex(name = UserEntityConstants.UserFollow.USER_FOLLOW_UNIQUE_INDEX, def = UserEntityConstants.UserFollow.USER_FOLLOW_UNIQUE_DEF, unique = true)
public class UserFollowEntity {

    @Id
    private String id;

    private String followerId;
    private String followingId;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
