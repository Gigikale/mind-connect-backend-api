package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.CommentDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<ApiResponse<String>> createComment(CommentDTO commentDto, String postId) throws MindConnectException;
}
