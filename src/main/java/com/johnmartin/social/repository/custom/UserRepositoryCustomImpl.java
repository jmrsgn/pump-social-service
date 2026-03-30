package com.johnmartin.social.repository.custom;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.johnmartin.social.constants.entities.UserEntityConstants;
import com.johnmartin.social.entities.UserEntity;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void incrementFollowingCount(String userId) {
        Update update = new Update().inc(UserEntityConstants.COLUMN_FOLLOWING_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(userId)), update, UserEntity.class);
    }

    @Override
    public void decrementFollowingCount(String userId) {
        Update update = new Update().inc(UserEntityConstants.COLUMN_FOLLOWING_COUNT, -1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(userId)), update, UserEntity.class);
    }

    @Override
    public void incrementFollowersCount(String userId) {
        Update update = new Update().inc(UserEntityConstants.COLUMN_FOLLOWERS_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(userId)), update, UserEntity.class);
    }

    @Override
    public void decrementFollowersCount(String userId) {
        Update update = new Update().inc(UserEntityConstants.COLUMN_FOLLOWERS_COUNT, -1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(userId)), update, UserEntity.class);
    }
}
