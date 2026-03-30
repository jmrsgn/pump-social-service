package com.johnmartin.social.service.facade;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.johnmartin.social.constants.UIConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.dto.request.UpdatePostRequest;
import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.dto.response.PostResponse;
import com.johnmartin.social.entities.PostEntity;
import com.johnmartin.social.entities.UserEntity;
import com.johnmartin.social.exception.BadRequestException;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.mapper.PostMapper;
import com.johnmartin.social.security.AuthContext;
import com.johnmartin.social.service.CommentService;
import com.johnmartin.social.service.PostLikeService;
import com.johnmartin.social.service.PostService;
import com.johnmartin.social.service.UserService;
import com.johnmartin.social.utilities.LoggerUtility;

import jakarta.transaction.Transactional;

@Service
public class PostCommentFacade {

    private static final Class<PostCommentFacade> clazz = PostCommentFacade.class;

    private final PostService postService;
    private final CommentService commentService;
    private final PostLikeService postLikeService;
    private final UserService userService;

    public PostCommentFacade(PostService postService,
                             CommentService commentService,
                             PostLikeService postLikeService,
                             UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.postLikeService = postLikeService;
        this.userService = userService;
    }

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
        if (authUser == null) {
            LoggerUtility.d(clazz, "Auth user is null, will throw unauthorized exception");
            throw new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }

        PageRequest pageRequest = PageRequest.of(page, UIConstants.MINIMUM_POSTS);
        Page<PostEntity> postPage = postService.getPostsWithLatestComments(pageRequest);
        List<PostEntity> posts = postPage.getContent();

        LoggerUtility.d(clazz, String.format("posts size: [%s]", posts.size()));
        if (CollectionUtils.isEmpty(posts)) {
            return Collections.emptyList();
        }

        // Extract all posts's id
        List<String> postIds = posts.stream().map(PostEntity::getId).toList();
        // Get latest comments by post ids
        UserEntity socialUser = userService.findByEmail(authUser.email());
        Map<String, List<CommentResponse>> commentsByPost = commentService.getLatestCommentsByPostIds(postIds,
                                                                                                      socialUser);
        // Get likedPostIds
        Set<String> likedPostIds = postLikeService.getLikedPostIds(postIds, socialUser.getId());
        return posts.stream()
                    .map(post -> PostMapper.toResponse(post,
                                                       commentsByPost.getOrDefault(post.getId(),
                                                                                   Collections.emptyList()),
                                                       socialUser,
                                                       likedPostIds.contains(post.getId())))
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

        if (StringUtils.isBlank(postId)) {
            LoggerUtility.d(clazz, "Post ID is blank, will not proceed with get post info API");
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        AuthUser authUser = AuthContext.get();
        if (authUser == null) {
            LoggerUtility.d(clazz, "Auth user is null, will throw unauthorized exception");
            throw new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }

        PostEntity post = postService.getPostById(postId);
        LoggerUtility.d(clazz, String.format("post: [%s]", post));

        // Get comments
        UserEntity socialUser = userService.findByEmail(authUser.email());
        Map<String, List<CommentResponse>> comments = commentService.getLatestCommentsByPostIds(Collections.singletonList(post.getId()),
                                                                                                socialUser);
        LoggerUtility.d(clazz, String.format("comments size: [%s]", comments.size()));
        boolean isLiked = postLikeService.isPostLikedByUser(postId, socialUser.getId());
        return PostMapper.toResponse(post,
                                     comments.getOrDefault(post.getId(), Collections.emptyList()),
                                     socialUser,
                                     isLiked);
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

        if (StringUtils.isBlank(postId)) {
            LoggerUtility.d(clazz, "Post ID is blank, will not proceed with like post API");
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        AuthUser authUser = AuthContext.get();
        if (authUser == null) {
            LoggerUtility.d(clazz, "Auth user is null, will throw unauthorized exception");
            throw new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }

        PostEntity post = postService.getPostById(postId);
        LoggerUtility.t(clazz, String.format("post: [%s]", post));

        UserEntity socialUser = userService.findByEmail(authUser.email());
        Map<String, List<CommentResponse>> comments = commentService.getLatestCommentsByPostIds(Collections.singletonList(post.getId()),
                                                                                                socialUser);
        LoggerUtility.d(clazz, String.format("comments size: [%s]", comments.size()));

        boolean isLiked = postLikeService.toggleLike(post.getId(), authUser.id());
        return PostMapper.toResponse(post, comments.getOrDefault(postId, Collections.emptyList()), socialUser, isLiked);
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

        if (StringUtils.isBlank(postId)) {
            LoggerUtility.d(clazz, "Post ID is blank, will not proceed with delete post API");
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        AuthUser authUser = AuthContext.get();
        if (authUser == null) {
            LoggerUtility.d(clazz, "Auth user is null, will throw unauthorized exception");
            throw new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }

        PostEntity post = postService.getPostById(postId);
        UserEntity user = userService.findByEmail(authUser.email());

        // Only post owner can delete
        if (!post.getAuthorId().equals(user.getId())) {
            throw new UnauthorizedException(ApiErrorMessages.User.YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }

        try {
            // Delete all comments under the post
            commentService.deleteByPostId(postId);

            // Delete likes for posts
            postLikeService.deleteByPostId(postId);

            // Delete the post
            postService.deletePost(postId);
        } catch (Exception e) {
            LoggerUtility.e(clazz, String.format("Failed to delete post. error: [%s]", e.getMessage()), e);
        }
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

        if (request == null) {
            LoggerUtility.d(clazz, "Request is null, will not proceed method call");
            throw new BadRequestException(ApiErrorMessages.INVALID_REQUEST);
        }

        if (StringUtils.isBlank(postId)) {
            LoggerUtility.d(clazz, "Post ID is blank, will not proceed with update post API");
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        AuthUser authUser = AuthContext.get();
        if (authUser == null) {
            LoggerUtility.d(clazz, "Auth user is null, will throw unauthorized exception");
            throw new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }

        PostEntity post = postService.getPostById(postId);

        // Only owner can edit
        if (!post.getAuthorId().equals(authUser.id())) {
            throw new UnauthorizedException(ApiErrorMessages.User.YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }

        // Update allowed fields only
        post.setTitle(request.title());
        post.setDescription(request.description());

        // Get social user
        UserEntity socialUser = userService.findByEmail(authUser.email());
        Map<String, List<CommentResponse>> comments = commentService.getLatestCommentsByPostIds(Collections.singletonList(post.getId()),
                                                                                                socialUser);
        LoggerUtility.d(clazz, String.format("comments size: [%s]", comments.size()));

        PostEntity updatedPost = postService.savePost(post);
        LoggerUtility.t(clazz, String.format("updatedPost: [%s]", updatedPost));

        boolean isLiked = postLikeService.isPostLikedByUser(postId, authUser.id());
        return PostMapper.toResponse(updatedPost,
                                     comments.getOrDefault(postId, Collections.emptyList()),
                                     socialUser,
                                     isLiked);
    }
}
