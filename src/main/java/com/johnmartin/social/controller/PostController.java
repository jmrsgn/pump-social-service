package com.johnmartin.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.dto.request.CreatePostRequest;
import com.johnmartin.social.dto.request.UpdatePostRequest;
import com.johnmartin.social.dto.response.PostResponse;
import com.johnmartin.social.dto.response.Result;
import com.johnmartin.social.service.PostService;
import com.johnmartin.social.service.facade.PostCommentFacade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping(ApiConstants.Path.API_POST)
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostCommentFacade postCommentFacade;

    @GetMapping
    public ResponseEntity<Result<List<PostResponse>>> getPosts(@RequestParam(defaultValue = "0") @PositiveOrZero int page) {
        List<PostResponse> posts = postCommentFacade.getPostsWithLatestComments(page);
        return ResponseEntity.ok(Result.success(posts));
    }

    @GetMapping(ApiConstants.Path.POST_INFO)
    public ResponseEntity<Result<PostResponse>> getPostInfo(@PathVariable @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId) {
        PostResponse response = postCommentFacade.getPostInfo(postId);
        return ResponseEntity.ok(Result.success(response));
    }

    @PostMapping
    public ResponseEntity<Result<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        PostResponse createdPost = postService.createPost(createPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdPost));
    }

    @PostMapping(ApiConstants.Path.POST_LIKE)
    public ResponseEntity<Result<PostResponse>> likePost(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId) {
        PostResponse response = postCommentFacade.likePost(postId);
        return ResponseEntity.ok(Result.success(response));
    }

    @DeleteMapping(ApiConstants.Path.POST_INFO)
    public ResponseEntity<Result<Void>> deletePost(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId) {
        postCommentFacade.deletePost(postId);
        return ResponseEntity.ok(Result.success(null));
    }

    @PutMapping(ApiConstants.Path.POST_INFO)
    public ResponseEntity<Result<PostResponse>> updatePost(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId,
                                                           @Valid @RequestBody UpdatePostRequest updatePostRequest) {
        PostResponse response = postCommentFacade.updatePost(postId, updatePostRequest);
        return ResponseEntity.ok(Result.success(response));
    }
}
