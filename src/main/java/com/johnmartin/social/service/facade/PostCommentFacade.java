package com.johnmartin.social.service.facade;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.johnmartin.social.constants.UIConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.dto.request.UpdatePostRequest;
import com.johnmartin.social.dto.response.PostResponse;
import com.johnmartin.social.entities.PostEntity;
import com.johnmartin.social.exception.BadRequestException;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.mapper.PostMapper;
import com.johnmartin.social.security.AuthContext;
import com.johnmartin.social.service.CommentService;
import com.johnmartin.social.service.PostService;
import com.johnmartin.social.utilities.LoggerUtility;

import jakarta.transaction.Transactional;

@Service
public class PostCommentFacade {

    private static final Class<PostCommentFacade> clazz = PostCommentFacade.class;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    /**
     * Get posts with 10 latest comments
     *
     * @param page
     *            - page
     * @return List of PostResponse
     */
    public List<PostResponse> getPostsWithLatestComments(int page) {
        LoggerUtility.d(clazz, String.format("Execute method: [getPostsWithLatestComments], page: [%d]", page));

        AuthUser authUser = AuthContext.get();

        PageRequest pageRequest = PageRequest.of(page, UIConstants.MINIMUM_POSTS);
        Page<PostEntity> postPage = postService.getPostsWithLatestComments(pageRequest);
        List<PostEntity> posts = postPage.getContent();

        return CollectionUtils.isEmpty(posts) ? Collections.emptyList()
                                              : posts.stream()
                                                     .map(post -> PostMapper.toResponse(post,
                                                                                        commentService.getComments(post.getId(),
                                                                                                                   authUser.getId(),
                                                                                                                   0),
                                                                                        authUser.getId()))
                                                     .toList();
    }

    /**
     * Get post info
     * 
     * @param postId
     *            - Post ID
     * @return PostResponse
     */
    public PostResponse getPostInfo(String postId) {
        LoggerUtility.d(clazz, String.format("Execute method: [getPostInfo] postId: [%s]", postId));

        AuthUser authUser = AuthContext.get();

        if (StringUtils.isBlank(postId)) {
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        PostEntity postToBeReturned = postService.getPostById(postId);
        LoggerUtility.d(clazz, String.format("postToBeReturned: [%s]", postToBeReturned));
        return PostMapper.toResponse(postToBeReturned,
                                     commentService.getComments(postToBeReturned.getId(), authUser.getId(), 0),
                                     authUser.getId());
    }

    /**
     * Like a post
     *
     * @param postId
     *            - Post ID
     * @return PostResponse
     */
    public PostResponse likePost(String postId) {
        LoggerUtility.d(clazz, String.format("Execute method: [likePost] postId: [%s]", postId));

        AuthUser authUser = AuthContext.get();

        if (StringUtils.isBlank(postId)) {
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        PostEntity post = postService.getPostById(postId);

        // If user already liked the post, unlike
        if (CollectionUtils.containsAny(post.getLikedByUserIds(), authUser.getId())) {
            postService.unlikePost(authUser.getId(), post.getId());
        } else {
            postService.likePost(authUser.getId(), post.getId());
        }

        // Get updated post to get updated like state
        PostEntity updatedPost = postService.getPostById(post.getId());
        LoggerUtility.t(clazz, String.format("updatedPost: [%s]", updatedPost));
        return PostMapper.toResponse(updatedPost,
                                     commentService.getComments(updatedPost.getId(), authUser.getId(), 0),
                                     authUser.getId());
    }

    /**
     * Delete a post
     *
     * @param postId
     *            - Post ID
     */
    @Transactional
    public void deletePost(String postId) {
        LoggerUtility.d(clazz, String.format("Execute method: [deletePost] postId: [%s]", postId));

        AuthUser authUser = AuthContext.get();

        if (StringUtils.isBlank(postId)) {
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        PostEntity post = postService.getPostById(postId);

        // Only post owner can delete
        if (!post.getAuthorId().equals(authUser.getId())) {
            throw new UnauthorizedException(ApiErrorMessages.User.YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }

        // Delete all comments under the post
        commentService.deleteByPostId(post.getId());

        // Delete the post
        postService.deletePost(post.getId());
    }

    /**
     * Update a post
     * 
     * @param postId
     *            - Post ID
     * @param request
     *            - UpdatePostRequest
     * @return PostResponse
     */
    @Transactional
    public PostResponse updatePost(String postId, UpdatePostRequest request) {
        LoggerUtility.d(clazz,
                        String.format("Execute method: [updatePost] postId: [%s] request: [%s]", postId, request));

        AuthUser authUser = AuthContext.get();

        if (StringUtils.isBlank(postId)) {
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        if (request == null) {
            throw new BadRequestException(ApiErrorMessages.INVALID_REQUEST);
        }

        PostEntity post = postService.getPostById(postId);

        // Only owner can edit
        if (!post.getAuthorId().equals(authUser.getId())) {
            throw new UnauthorizedException(ApiErrorMessages.User.YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }

        // Update allowed fields only
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setUpdatedAt(Instant.now());

        PostEntity updatedPost = postService.savePost(post);
        LoggerUtility.t(clazz, String.format("updatedPost: [%s]", updatedPost));
        return PostMapper.toResponse(updatedPost,
                                     commentService.getComments(updatedPost.getId(), authUser.getId(), 0),
                                     authUser.getId());
    }
}
