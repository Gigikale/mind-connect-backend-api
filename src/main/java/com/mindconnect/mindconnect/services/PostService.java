package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.PostDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;


public interface PostService {
    ResponseEntity<ApiResponse<String>> createPost(PostDTO postDto) throws MindConnectException;
}

