package com.johnmartin.social.entities;

import java.time.Instant;
import java.util.Objects;

import org.springframework.data.annotation.Id;
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
    private int followersNo = 0;
    private int followingNo = 0;
    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity that = (UserEntity) o;
        return followersNo == that.followersNo && followingNo == that.followingNo && Objects.equals(id, that.id)
               && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName)
               && Objects.equals(email, that.email) && Objects.equals(profileImageUrl, that.profileImageUrl)
               && Objects.equals(bio, that.bio) && Objects.equals(createdAt, that.createdAt)
               && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                            firstName,
                            lastName,
                            email,
                            profileImageUrl,
                            bio,
                            followersNo,
                            followingNo,
                            createdAt,
                            updatedAt);
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
               + '\'' + ", email='" + email + '\'' + ", profileImageUrl='" + profileImageUrl + '\'' + ", bio='" + bio
               + '\'' + ", followersNo=" + followersNo + ", followingNo=" + followingNo + ", createdAt=" + createdAt
               + ", updatedAt=" + updatedAt + '}';
    }
}
