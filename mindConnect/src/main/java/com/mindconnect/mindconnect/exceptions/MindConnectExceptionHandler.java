package com.mindconnect.mindconnect.exceptions;

import com.mindconnect.mindconnect.payloads.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
@RestControllerAdvice
public class MindConnectExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MindConnectException.class)
    public ResponseEntity<?> handleMindConnectException(MindConnectException exception){
        ApiResponse<String> response = new ApiResponse<>(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResponse<String>UsernameNotFoundException(UsernameNotFoundException exception){
        return new ApiResponse<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<String> handleConstraintViolationException(ConstraintViolationException exception){
        return new ApiResponse<>(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<String> handleBadCredentialsException(){
        return new ApiResponse<>(
                "Incorrect user details",
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ApiResponse<String> handleExpiredJwtException(ExpiredJwtException e){
        return new ApiResponse<>(
                e.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ApiResponse<String> handleMalformedJwtException(){
        return new ApiResponse<>(
                "Incorrect token",
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(LockedException.class)
    public ApiResponse<String> handleLockedException(LockedException exception){
        return new ApiResponse<>(
                "Email is not verified. Check your email for verification link",
                HttpStatus.BAD_REQUEST
        );
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request
    ) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        ApiResponse<List<String>> response = new ApiResponse<>();
        response.setData(errors);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Validation error");
        response.setStatusCode(status.value());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<?>> handleNullPointerException(NullPointerException ex) {
        ApiResponse<?> errorResponse = new ApiResponse<>("NullPointerException occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(PostCreationFailedException.class)
    public ResponseEntity<ApiResponse<?>> handlePostCreationFailedException(PostCreationFailedException ex) {
        ApiResponse<String> response = new ApiResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CommentCreationFailedException.class)
    public ResponseEntity<ApiResponse<?>> handleCommentCreationFailedException(CommentCreationFailedException ex) {
        ApiResponse<String> response = new ApiResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
