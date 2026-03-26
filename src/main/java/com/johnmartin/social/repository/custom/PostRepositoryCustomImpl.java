package com.johnmartin.social.repository.custom;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.johnmartin.social.constants.entities.PostEntityConstants;
import com.johnmartin.social.entities.PostEntity;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public PostRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void incrementCommentsCount(String postId) {
        Update update = new Update().inc(PostEntityConstants.COLUMN_COMMENTS_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(postId)), update, PostEntity.class);
    }

    @Override
    public void decrementCommentsCount(String postId) {
        Update update = new Update().inc(PostEntityConstants.COLUMN_COMMENTS_COUNT, -1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(postId)), update, PostEntity.class);
    }

    @Override
    public void incrementLikesCount(String postId) {
        Update update = new Update().inc(PostEntityConstants.COLUMN_LIKES_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(postId)), update, PostEntity.class);
    }

    @Override
    public void decrementLikesCount(String postId) {
        Update update = new Update().inc(PostEntityConstants.COLUMN_LIKES_COUNT, -1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(postId)), update, PostEntity.class);
    }
}
