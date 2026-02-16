package com.johnmartin.social.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.johnmartin.social.constants.entities.PostEntityConstants;
import com.johnmartin.social.entities.PostEntity;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

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
    public void likePost(String userId, String postId) {
        Update update = new Update().addToSet(PostEntityConstants.COLUMN_LIKED_BY_USER_IDS, userId)
                                    .inc(PostEntityConstants.COLUMN_LIKES_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(postId)), update, PostEntity.class);
    }

    @Override
    public void unlikePost(String userId, String postId) {
        Update update = new Update().pull(PostEntityConstants.COLUMN_LIKED_BY_USER_IDS, userId)
                                    .inc(PostEntityConstants.COLUMN_LIKES_COUNT, -1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(postId)), update, PostEntity.class);
    }
}
