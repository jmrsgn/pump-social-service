package com.johnmartin.social.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.social.entities.UserEntity;
import com.johnmartin.social.repository.custom.UserRepositoryCustom;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String>, UserRepositoryCustom {
}
