package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.LoginDto;
import com.mindconnect.mindconnect.dtos.LoginResponse;
import com.mindconnect.mindconnect.dtos.SignupDto;
import com.mindconnect.mindconnect.enums.VerifyType;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthServices {
     ResponseEntity<ApiResponse<LoginResponse>> login(LoginDto loginDto);
     ResponseEntity<ApiResponse<String>> resetPassword(String token, String password, String confirmPassword);
     ResponseEntity<ApiResponse<String>> checkEmailForPasswordReset(String emailAddress);
     ResponseEntity<ApiResponse<String>> signup(SignupDto signupDto);
     ApiResponse<String> confirmEmail(String token);
     ResponseEntity<ApiResponse<String>> resendLink(String emailAddress, VerifyType verifyType);

}
