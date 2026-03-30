package com.johnmartin.social.dto.response;

public record UserResponse(String id,
                           String firstName,
                           String lastName,
                           String email,
                           String profileImageUrl,
                           String bio,
                           long followersNo,
                           long followingNo) {
}
