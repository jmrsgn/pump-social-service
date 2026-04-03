package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.johnmartin.social.constants.entities.UserEntityConstants;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = UserEntityConstants.TABLE_NAME)
public class UserEntity {
    @Id
    private String id;
    @Field(name = UserEntityConstants.COLUMN_FIRST_NAME)
    private String firstName;
    @Field(name = UserEntityConstants.COLUMN_LAST_NAME)
    private String lastName;
    @Field(name = UserEntityConstants.COLUMN_EMAIL)
    private String email;
    @Field(name = UserEntityConstants.COLUMN_PROFILE_IMAGE_URL)
    private String profileImageUrl;
    @Field(name = UserEntityConstants.COLUMN_BIO)
    private String bio;

    @Field(name = UserEntityConstants.COLUMN_FOLLOWERS_COUNT)
    private long followersCount = 0;
    @Field(name = UserEntityConstants.COLUMN_FOLLOWING_COUNT)
    private long followingCount = 0;

    @CreatedDate
    @Field(name = UserEntityConstants.COLUMN_CREATED_AT)
    private Instant createdAt;
    @LastModifiedDate
    @Field(name = UserEntityConstants.COLUMN_UPDATED_AT)
    private Instant updatedAt;

    @Override
    public String toString() {
        return "UserEntity{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
               + '\'' + ", email='" + email + '\'' + ", profileImageUrl='" + profileImageUrl + '\'' + ", bio='" + bio
               + '\'' + ", followersCount=" + followersCount + ", followingCount=" + followingCount + ", createdAt="
               + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
