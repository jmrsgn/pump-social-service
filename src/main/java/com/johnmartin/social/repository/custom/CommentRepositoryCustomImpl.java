package com.johnmartin.social.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.johnmartin.social.constants.entities.CommentEntityConstants;
import com.johnmartin.social.entities.CommentEntity;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void likeComment(String userId, String commentId) {
        Update update = new Update().addToSet(CommentEntityConstants.COLUMN_LIKED_BY_USER_IDS, userId)
                                    .inc(CommentEntityConstants.COLUMN_LIKES_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(commentId)), update, CommentEntity.class);
    }

    @Override
    public void unlikeComment(String userId, String commentId) {
        Update update = new Update().pull(CommentEntityConstants.COLUMN_LIKED_BY_USER_IDS, userId)
                                    .inc(CommentEntityConstants.COLUMN_LIKES_COUNT, -1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(commentId)), update, CommentEntity.class);
    }
}
