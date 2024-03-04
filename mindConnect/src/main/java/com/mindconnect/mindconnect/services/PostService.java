package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.PostDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public interface PostService {
    ResponseEntity<ApiResponse<String>> createPost(PostDTO postDto) throws MindConnectException;

    ResponseEntity<ApiResponse<String>> reportPost(UUID postId, String reportReason) throws MindConnectException;

    ResponseEntity<ApiResponse<String>> updatePost(UUID postId, PostDTO postDTO);
    ResponseEntity<ApiResponse<String>> deletePost(UUID postId);
}

