package com.johnmartin.social.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String authorId;
    private String title;
    private String description;

    private String mediaUrl;
    private MediaType mediaType;

    private int likesCount;
    private int commentsCount;
    private int sharesCount;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @Override
    public String toString() {
        return "PostEntity{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='" + description + '\''
               + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", likesCount=" + likesCount
               + ", commentsCount=" + commentsCount + ", sharesCount=" + sharesCount + '}';
    }
}
