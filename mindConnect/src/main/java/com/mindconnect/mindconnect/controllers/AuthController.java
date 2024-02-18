package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.LoginDto;
import com.mindconnect.mindconnect.dtos.ResetPasswordDto;
import com.mindconnect.mindconnect.dtos.LoginResponse;
import com.mindconnect.mindconnect.dtos.SignupDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.AuthServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServices authServices;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody @Validated SignupDto signup) {
        return authServices.signup(signup);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginDto loginDto){
        return authServices.login(loginDto);
    }

    @GetMapping("/check-email-for-password-reset")
    public ResponseEntity<ApiResponse<String>> checkEmailForPasswordReset(@RequestParam String emailAddress) {
        return authServices.checkEmailForPasswordReset(emailAddress);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @RequestParam String token, @RequestBody ResetPasswordDto body
    ) {
        return authServices.resetPassword(token, body.newPassword(), body.confirmPassword());
    }
}
