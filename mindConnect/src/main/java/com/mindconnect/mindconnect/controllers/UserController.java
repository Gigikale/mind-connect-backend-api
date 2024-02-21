package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.dtos.ChangePasswordDto;
import com.mindconnect.mindconnect.dtos.ProfileDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.payloads.PostHistory;
import com.mindconnect.mindconnect.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PatchMapping("/password/change")
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

    @GetMapping("/news/feed")
    public ResponseEntity<ApiResponse<List<PostHistory>>> getPosts(@RequestParam(required = false) Boolean userId,
                                                                   @RequestParam(required = false) UUID groupId,
                                                                   @RequestParam(defaultValue = "0") Integer page,
                                                                   @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.ok().body(userService.viewPosts(userId, groupId, page,size));
    }
}
