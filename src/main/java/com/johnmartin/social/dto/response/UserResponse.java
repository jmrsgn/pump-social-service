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
}
