package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.johnmartin.social.constants.entities.UserEntityConstants;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = UserEntityConstants.TABLE_NAME)
public class UserEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImageUrl;
    private String bio;

    private long followersCount = 0;
    private long followingCount = 0;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @Override
    public String toString() {
        return "UserEntity{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
               + '\'' + ", email='" + email + '\'' + ", profileImageUrl='" + profileImageUrl + '\'' + ", bio='" + bio
               + '\'' + ", followersCount=" + followersCount + ", followingCount=" + followingCount + ", createdAt="
               + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
