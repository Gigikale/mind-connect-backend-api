package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.LoginDto;
import com.mindconnect.mindconnect.dtos.LoginResponse;
import com.mindconnect.mindconnect.dtos.SignupDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthServices {
     ResponseEntity<ApiResponse<LoginResponse>> login(LoginDto loginDto);
     ResponseEntity<ApiResponse<String>> signup(SignupDto signupDto);

}
