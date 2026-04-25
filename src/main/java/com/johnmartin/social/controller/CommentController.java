package com.johnmartin.social.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.error.ValidationErrorConstants;
import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.dto.response.common.Result;
import com.johnmartin.social.service.CommentService;

import jakarta.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping(ApiConstants.Path.API_COMMENT)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Result<CommentResponse>> createComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ValidationErrorConstants.POST_ID_IS_REQUIRED) String postId,
                                                                 @RequestBody @NotBlank(message = ValidationErrorConstants.COMMENT_IS_REQUIRED) String comment) {
        CommentResponse createdComment = commentService.createComment(postId, comment, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdComment));
    }

    @PostMapping(ApiConstants.Path.COMMENT_REPLIES)
    public ResponseEntity<Result<CommentResponse>> createReply(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ValidationErrorConstants.POST_ID_IS_REQUIRED) String postId,
                                                               @PathVariable(ApiConstants.Params.COMMENT_ID) @NotBlank(message = ValidationErrorConstants.COMMENT_ID_IS_REQUIRED) String commentId,
                                                               @RequestBody @NotBlank(message = ValidationErrorConstants.COMMENT_IS_REQUIRED) String comment) {
        CommentResponse createdReply = commentService.createReply(postId, commentId, comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdReply));
    }

    @DeleteMapping(ApiConstants.Path.COMMENT_INFO)
    public ResponseEntity<Result<Void>> deleteComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ValidationErrorConstants.POST_ID_IS_REQUIRED) String postId,
                                                      @PathVariable(ApiConstants.Params.COMMENT_ID) @NotBlank(message = ValidationErrorConstants.COMMENT_ID_IS_REQUIRED) String commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(Result.success(null));
    }

    @PostMapping(ApiConstants.Path.COMMENT_LIKE)
    public ResponseEntity<Result<CommentResponse>> likeComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ValidationErrorConstants.POST_ID_IS_REQUIRED) String postId,
                                                               @PathVariable(ApiConstants.Params.COMMENT_ID) @NotBlank(message = ValidationErrorConstants.COMMENT_ID_IS_REQUIRED) String commentId) {
        CommentResponse response = commentService.likeComment(postId, commentId);
        return ResponseEntity.ok(Result.success(response));
    }
}
