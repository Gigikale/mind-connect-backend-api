package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.ChangePasswordDto;
import com.mindconnect.mindconnect.dtos.ProfileDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.payloads.PostHistory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordDto changePasswordDto);

    ResponseEntity<ApiResponse<String>> editProfile(ProfileDto profileDto);

    ApiResponse<List<PostHistory>> viewPosts(Boolean userId, UUID groupId, Integer page, Integer size);
}
