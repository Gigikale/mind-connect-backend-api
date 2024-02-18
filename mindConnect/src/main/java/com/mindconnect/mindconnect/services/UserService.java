package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.ChangePasswordDto;
import com.mindconnect.mindconnect.dtos.ProfileDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordDto changePasswordDto);

    ResponseEntity<ApiResponse<String>> editProfile(ProfileDto profileDto);
}
