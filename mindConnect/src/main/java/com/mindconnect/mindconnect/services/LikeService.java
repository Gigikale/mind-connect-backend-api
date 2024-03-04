package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.payloads.ApiResponse;

import java.util.UUID;

public interface LikeService {
    ApiResponse<String> likePost(UUID post);
    ApiResponse<String> likeComment(UUID comment);
}
