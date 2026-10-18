package com.finTech.Expense_TrackerV1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(
            ResourceNotFoundException exception
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ApiResponse(false, exception.getMessage(),exception.getErrorCode(), null)
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception
    ){
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .filter(java.util.Objects::nonNull)
                .collect(java.util.stream.Collectors.joining("; "));

        if (message.isBlank()) {
            message = "Validation failed. Please check your request.";
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse(
                                false,
                                message,
                                ErrorCode.VALIDATION_ERROR,
                                null
                        )
                );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse> CategoryAlreadyExists(
            DuplicateResourceException duplicateResourceException
    ){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new ApiResponse(false, duplicateResourceException.getMessage(), duplicateResourceException.getErrorCode(),null)
                );
    }
}
