package com.johnmartin.social.repository.custom;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.johnmartin.social.constants.entities.CommentEntityConstants;
import com.johnmartin.social.entities.CommentEntity;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public CommentRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void incrementLikesCount(String commentId) {
        Update update = new Update().inc(CommentEntityConstants.COLUMN_LIKES_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(commentId)), update, CommentEntity.class);
    }

    @Override
    public void decrementLikesCount(String commentId) {
        Update update = new Update().inc(CommentEntityConstants.COLUMN_LIKES_COUNT, -1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(commentId)), update, CommentEntity.class);
    }

    @Override
    public void incrementRepliesCount(String commentId) {
        Update update = new Update().inc(CommentEntityConstants.COLUMN_REPLIES_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(commentId)), update, CommentEntity.class);
    }

    @Override
    public void decrementRepliesCount(String commentId) {
        Update update = new Update().inc(CommentEntityConstants.COLUMN_REPLIES_COUNT, -1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(commentId)), update, CommentEntity.class);
    }
}
