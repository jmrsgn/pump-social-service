package com.johnmartin.social.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.johnmartin.social.entity.UserEntity;
import com.johnmartin.social.repository.custom.UserRepositoryCustom;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String>, UserRepositoryCustom {

    List<UserEntity> findByIdIn(List<String> ids);

    /**
     * Searches users by first name or last name while excluding the current authenticated user.
     *
     * @param currentUserId
     *            - ID of the authenticated user to exclude from the search results
     * @param query
     *            - Search keyword matched against the user's first or last name
     * @return List of matching users excluding the authenticated user
     */
    @Query("""
            {
              "_id": { "$ne": ?0 },
              "$or": [
                { "first_name": { "$regex": ?1, "$options": "i" } },
                { "last_name": { "$regex": ?1, "$options": "i" } }
              ]
            }
            """)
    List<UserEntity> searchUsers(String currentUserId, String query);
}
