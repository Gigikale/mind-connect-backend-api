package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.ChangePasswordDto;
import com.mindconnect.mindconnect.dtos.ProfileDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PatchMapping("/password_change")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestBody ChangePasswordDto changePasswordDto
    ) {
        return userService.changePassword(changePasswordDto);
    }


    @PatchMapping("/")
    public ResponseEntity<ApiResponse<String>> editProfile(
            @RequestBody ProfileDto profileDto){
        return userService.editProfile(profileDto);
    }
}
