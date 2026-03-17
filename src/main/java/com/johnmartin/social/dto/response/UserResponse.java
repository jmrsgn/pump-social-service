package com.johnmartin.social.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImageUrl;
    private String bio;
    private int followersNo;
    private int followingNo;

    public UserResponse withId(String id) {
        setId(id);
        return this;
    }

    public UserResponse withFirstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public UserResponse withLastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public UserResponse withEmail(String email) {
        setEmail(email);
        return this;
    }

    public UserResponse withProfileImageUrl(String profileImageUrl) {
        setProfileImageUrl(profileImageUrl);
        return this;
    }

    public UserResponse withBio(String bio) {
        setBio(bio);
        return this;
    }

    public UserResponse withFollowersNo(int followersNo) {
        setFollowersNo(followersNo);
        return this;
    }

    public UserResponse withFollowingNo(int followingNo) {
        setFollowingNo(followingNo);
        return this;
    }
}
