package com.johnmartin.social.service.facade;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.johnmartin.social.constants.UIConstants;
import com.johnmartin.social.constants.error.AuthErrorConstants;
import com.johnmartin.social.constants.error.SystemErrorConstants;
import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.dto.request.UpdatePostRequest;
import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.dto.response.PostResponse;
import com.johnmartin.social.dto.response.common.PagedResponse;
import com.johnmartin.social.entities.PostEntity;
import com.johnmartin.social.entities.UserEntity;
import com.johnmartin.social.exception.BadRequestException;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.mapper.PostMapper;
import com.johnmartin.social.service.*;
import com.johnmartin.social.utilities.LoggerUtility;
import com.johnmartin.social.utils.ExtractorUtils;

import jakarta.transaction.Transactional;

@Service
public class PostCommentFacade {

    private static final Class<PostCommentFacade> clazz = PostCommentFacade.class;

    private final PostService postService;
    private final CommentService commentService;
    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;
    private final UserService userService;
    private final AuthService authService;

    public PostCommentFacade(PostService postService,
                             CommentService commentService,
                             PostLikeService postLikeService,
                             CommentLikeService commentLikeService,
                             UserService userService,
                             AuthService authService) {
        this.postService = postService;
        this.commentService = commentService;
        this.postLikeService = postLikeService;
        this.commentLikeService = commentLikeService;
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * Get posts with 10 latest comments
     *
     * @param page
     *            - page
     * @return List of PostResponse
     */
    public PagedResponse<PostResponse> getPostsWithLatestComments(int page) {
        LoggerUtility.d(clazz, String.format("Execute method: [getPostsWithLatestComments], page: [%d]", page));

        // Get auth user
        AuthUser authUser = authService.getAuthUser();

        // Get posts per page
        PageRequest pageRequest = PageRequest.of(page, UIConstants.MINIMUM_POSTS);
        Page<PostEntity> postsPage = postService.getPostsWithLatestComments(pageRequest);
        List<PostEntity> posts = postsPage.getContent();
        LoggerUtility.d(clazz, String.format("posts size: [%s]", posts.size()));

        if (CollectionUtils.isEmpty(posts)) {
            LoggerUtility.d(clazz, "posts are empty");
            return new PagedResponse<>(Collections.emptyList(), page, UIConstants.MINIMUM_POSTS, 0, 0, false);
        }

        // Extract all posts's id
        List<String> postIds = ExtractorUtils.extractToList(posts, PostEntity::getId);
        LoggerUtility.d(clazz, String.format("postIds size: [%s]", postIds.size()));

        // Get authorIds
        Set<String> authorIds = ExtractorUtils.extractToSet(posts, PostEntity::getAuthorId);
        LoggerUtility.d(clazz, String.format("authorIds size: [%s]", authorIds.size()));

        // Get users tied to authorIds
        List<UserEntity> authors = userService.findByIdIn(authorIds.stream().toList());
        LoggerUtility.d(clazz, String.format("authors size: [%s]", authors.size()));

        // Get users by id
        Map<String, UserEntity> usersById = ExtractorUtils.extractToMap(authors,
                                                                        UserEntity::getId,
                                                                        Function.identity());
        LoggerUtility.d(clazz, String.format("usersById size: [%s]", usersById.size()));

        // Get social user
        UserEntity socialUser = userService.findById(authUser.id());
        // Get latest comments per post
        Map<String, List<CommentResponse>> commentsByPost = commentService.getLatestCommentsByPostIds(postIds,
                                                                                                      socialUser);
        LoggerUtility.d(clazz, String.format("commentsByPost size: [%s]", commentsByPost.size()));

        // Get likedPostIds
        Set<String> likedPostIds = postLikeService.getLikedPostIds(postIds, socialUser.getId());
        LoggerUtility.d(clazz, String.format("likedPostIds size: [%s]", likedPostIds.size()));

        return postProcessGetPostsWithLatestComments(postsPage,
                                                     posts,
                                                     usersById,
                                                     likedPostIds,
                                                     socialUser,
                                                     commentsByPost);
    }

    private PagedResponse<PostResponse> postProcessGetPostsWithLatestComments(Page<PostEntity> postPage,
                                                                              List<PostEntity> posts,
                                                                              Map<String, UserEntity> usersById,
                                                                              Set<String> likedPostIds,
                                                                              UserEntity socialUser,
                                                                              Map<String, List<CommentResponse>> commentsByPost) {
        LoggerUtility.d(clazz, "Execute method: [postProcessGetPostsWithLatestComments]");

        // Prepare post response list
        List<PostResponse> postResponseList = posts.stream().map(post -> {
            UserEntity author = usersById.get(post.getAuthorId());
            boolean isLikedByCurrentUser = likedPostIds.contains(post.getId());
            boolean isOwnedByCurrentUser = socialUser.getId().equals(post.getAuthorId());

            LoggerUtility.d(clazz,
                            String.format("author: [%s] isLikedByCurrentUser: [%s] isOwnedByCurrentUser: [%s]",
                                          author,
                                          isLikedByCurrentUser,
                                          isOwnedByCurrentUser));

            return PostMapper.toResponse(post,
                                         commentsByPost.getOrDefault(post.getId(), Collections.emptyList()),
                                         author,
                                         isLikedByCurrentUser,
                                         isOwnedByCurrentUser);
        }).toList();

        LoggerUtility.d(clazz, String.format("postResponseList size: [%s]", postResponseList.size()));

        return new PagedResponse<>(postResponseList,
                                   postPage.getNumber(),
                                   postPage.getSize(),
                                   postPage.getTotalElements(),
                                   postPage.getTotalPages(),
                                   postPage.hasNext());
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

        // Get auth user
        AuthUser authUser = authService.getAuthUser();

        // Get post
        PostEntity post = postService.getPostById(postId);
        LoggerUtility.d(clazz, String.format("post: [%s]", post));

        // Get social user
        UserEntity socialUser = userService.findById(authUser.id());
        Map<String, List<CommentResponse>> comments = commentService.getLatestCommentsByPostIds(Collections.singletonList(post.getId()),
                                                                                                socialUser);
        LoggerUtility.d(clazz, String.format("comments size: [%s]", comments.size()));

        boolean isLikedByCurrentUser = postLikeService.isPostLikedByUser(postId, socialUser.getId());
        boolean isOwnedByCurrentUser = socialUser.getId().equals(post.getAuthorId());

        LoggerUtility.d(clazz, String.format("isLikedByCurrentUser: [%s]", isLikedByCurrentUser));
        LoggerUtility.d(clazz, String.format("isOwnedByCurrentUser: [%s]", isOwnedByCurrentUser));

        return PostMapper.toResponse(post,
                                     comments.getOrDefault(post.getId(), Collections.emptyList()),
                                     socialUser,
                                     isLikedByCurrentUser,
                                     isOwnedByCurrentUser);
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

        // Get auth user
        AuthUser authUser = authService.getAuthUser();

        boolean isLikedByCurrentUser = postLikeService.toggleLike(postId, authUser.id());
        LoggerUtility.d(clazz, String.format("isLikedByCurrentUser: [%s]", isLikedByCurrentUser));

        // Get updated post info
        PostEntity post = postService.getPostById(postId);
        LoggerUtility.d(clazz, String.format("post: [%s]", post));

        // Get social user
        UserEntity socialUser = userService.findById(authUser.id());
        boolean isOwnedByCurrentUser = socialUser.getId().equals(post.getAuthorId());
        LoggerUtility.d(clazz, String.format("isOwnedByCurrentUser: [%s]", isOwnedByCurrentUser));

        // Get comments of liked post
        Map<String, List<CommentResponse>> comments = commentService.getLatestCommentsByPostIds(Collections.singletonList(postId),
                                                                                                socialUser);
        LoggerUtility.d(clazz, String.format("comments size: [%s]", comments.size()));

        // Get post author
        UserEntity postAuthor = userService.findById(post.getAuthorId());
        return PostMapper.toResponse(post,
                                     comments.getOrDefault(postId, Collections.emptyList()),
                                     postAuthor,
                                     isLikedByCurrentUser,
                                     isOwnedByCurrentUser);
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

        // Get auth user
        AuthUser authUser = authService.getAuthUser();

        // Get post
        PostEntity post = postService.getPostById(postId);

        // Get social user
        UserEntity socialUser = userService.findById(authUser.id());

        // Only post owner can delete
        if (!post.getAuthorId().equals(socialUser.getId())) {
            throw new UnauthorizedException(AuthErrorConstants.YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
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
            throw new BadRequestException(SystemErrorConstants.INVALID_REQUEST);
        }

        // Get auth user
        AuthUser authUser = authService.getAuthUser();

        // Get post
        PostEntity post = postService.getPostById(postId);

        // Only owner can edit
        if (!post.getAuthorId().equals(authUser.id())) {
            throw new UnauthorizedException(AuthErrorConstants.YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }

        // Update allowed fields only
        post.setTitle(request.title());
        post.setDescription(request.description());

        // Get social user
        UserEntity socialUser = userService.findById(authUser.id());
        // Get latest comments of updated post
        Map<String, List<CommentResponse>> comments = commentService.getLatestCommentsByPostIds(Collections.singletonList(post.getId()),
                                                                                                socialUser);
        LoggerUtility.d(clazz, String.format("comments size: [%s]", comments.size()));

        PostEntity updatedPost = postService.savePost(post);
        LoggerUtility.t(clazz, String.format("updatedPost: [%s]", updatedPost));

        boolean isLikedByCurrentUser = postLikeService.isPostLikedByUser(postId, authUser.id());
        boolean isOwnedByCurrentUser = socialUser.getId().equals(post.getAuthorId());

        LoggerUtility.d(clazz, String.format("isLikedByCurrentUser: [%s]", isLikedByCurrentUser));
        LoggerUtility.d(clazz, String.format("isOwnedByCurrentUser: [%s]", isOwnedByCurrentUser));

        return PostMapper.toResponse(updatedPost,
                                     comments.getOrDefault(postId, Collections.emptyList()),
                                     socialUser,
                                     isLikedByCurrentUser,
                                     isOwnedByCurrentUser);
    }
}
