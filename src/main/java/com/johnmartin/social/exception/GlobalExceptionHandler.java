package com.johnmartin.social.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.utils.ApiErrorUtils;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        return ApiErrorUtils.createBadRequestErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
        return ApiErrorUtils.createUnauthorizedErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ApiErrorUtils.createNotFoundErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(ConflictException ex) {
        return ApiErrorUtils.createConflictErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ApiErrorUtils.createInternalServerErrorResponse(ApiErrorMessages.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle method argument not valid exception (used to validate request objects)
     * 
     * @param ex
     *            - MethodArgumentNotValidException
     * @return ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // Get the first error message will be thrown in Bean annotations for requests
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ApiErrorUtils.createBadRequestErrorResponse(message);
    }

    /**
     * Handle constraint violated exception (used for validating path variables)
     *
     * @param ex
     *            - ConstraintViolationException
     * @return ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return ApiErrorUtils.createBadRequestErrorResponse(message);
    }

}
