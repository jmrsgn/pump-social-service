package com.johnmartin.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.dto.request.CreateCommentRequest;
import com.johnmartin.social.dto.response.CommentResponse;
import com.johnmartin.social.dto.response.Result;
import com.johnmartin.social.service.CommentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
@RestController
@RequestMapping(ApiConstants.Path.API_COMMENT)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Result<CommentResponse>> createComment(@PathVariable(ApiConstants.Params.POST_ID) String postId,
                                                                 @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        CommentResponse createdComment = commentService.createComment(postId, createCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdComment));
    }

    @DeleteMapping(ApiConstants.Path.COMMENT_INFO)
    public ResponseEntity<Result<Void>> deleteComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId,
                                                      @PathVariable(ApiConstants.Params.COMMENT_ID) @NotBlank(message = ApiErrorMessages.Comment.COMMENT_ID_IS_REQUIRED) String commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(Result.success(null));
    }

    @PostMapping(ApiConstants.Path.COMMENT_LIKE)
    public ResponseEntity<Result<CommentResponse>> likeComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId,
                                                               @PathVariable(ApiConstants.Params.COMMENT_ID) @NotBlank(message = ApiErrorMessages.Comment.COMMENT_ID_IS_REQUIRED) String commentId) {
        CommentResponse response = commentService.likeComment(postId, commentId);
        return ResponseEntity.ok(Result.success(response));
    }
}
