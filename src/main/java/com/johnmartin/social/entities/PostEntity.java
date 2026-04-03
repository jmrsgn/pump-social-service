package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.johnmartin.social.constants.entities.PostEntityConstants;
import com.johnmartin.social.enums.MediaType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = PostEntityConstants.TABLE_NAME)
public class PostEntity {

    @Id
    private String id;

    @Field(name = PostEntityConstants.COLUMN_AUTHOR_ID)
    private String authorId;
    @Field(name = PostEntityConstants.COLUMN_TITLE)
    private String title;
    @Field(name = PostEntityConstants.COLUMN_DESCRIPTION)
    private String description;

    @Field(name = PostEntityConstants.COLUMN_MEDIA_URL)
    private String mediaUrl;
    @Field(name = PostEntityConstants.COLUMN_MEDIA_TYPE)
    private MediaType mediaType;

    @Field(name = PostEntityConstants.COLUMN_LIKES_COUNT)
    private int likesCount;
    @Field(name = PostEntityConstants.COLUMN_COMMENTS_COUNT)
    private int commentsCount;
    @Field(name = PostEntityConstants.COLUMN_SHARES_COUNT)
    private int sharesCount;

    @CreatedDate
    @Field(name = PostEntityConstants.COLUMN_CREATED_AT)
    private Instant createdAt;
    @LastModifiedDate
    @Field(name = PostEntityConstants.COLUMN_UPDATED_AT)
    private Instant updatedAt;

    @Override
    public String toString() {
        return "PostEntity{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='" + description + '\''
               + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", likesCount=" + likesCount
               + ", commentsCount=" + commentsCount + ", sharesCount=" + sharesCount + '}';
    }
}
