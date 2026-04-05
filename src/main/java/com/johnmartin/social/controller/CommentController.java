package com.johnmartin.social.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.dto.request.CreateCommentRequest;
import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.dto.response.common.Result;
import com.johnmartin.social.service.CommentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
@RestController
@RequestMapping(ApiConstants.Path.API_COMMENT)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Result<CommentResponse>> createComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = "Post ID is required") String postId,
                                                                 @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        CommentResponse createdComment = commentService.createComment(postId, createCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdComment));
    }

    @DeleteMapping(ApiConstants.Path.COMMENT_INFO)
    public ResponseEntity<Result<Void>> deleteComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = "Post ID is required") String postId,
                                                      @PathVariable(ApiConstants.Params.COMMENT_ID) @NotBlank(message = "Comment ID is required") String commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(Result.success(null));
    }

    @PostMapping(ApiConstants.Path.COMMENT_LIKE)
    public ResponseEntity<Result<CommentResponse>> likeComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = "Post ID is required") String postId,
                                                               @PathVariable(ApiConstants.Params.COMMENT_ID) @NotBlank(message = "Comment ID is required") String commentId) {
        CommentResponse response = commentService.likeComment(postId, commentId);
        return ResponseEntity.ok(Result.success(response));
    }
}
