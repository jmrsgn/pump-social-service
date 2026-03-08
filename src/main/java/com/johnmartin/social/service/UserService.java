package com.johnmartin.social.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.johnmartin.social.dto.request.CreateUserRequest;
import com.johnmartin.social.entities.UserEntity;
import com.johnmartin.social.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> createUser(CreateUserRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(request.getId());
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getEmail());
        userEntity.setProfileImageUrl(StringUtils.EMPTY);
        userEntity.setBio(StringUtils.EMPTY);

        return Optional.of(userRepository.save(userEntity));
    }

    public Optional<UserEntity> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        // Registered as subject for Auth, is email
        return userRepository.findByEmail(auth.getName());
    }
}
