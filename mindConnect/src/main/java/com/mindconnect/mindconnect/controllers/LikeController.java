package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.LikeDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/post")
    public ResponseEntity<ApiResponse<String>> likePost(@RequestBody LikeDto likeDto) {
        ApiResponse<String> response = likeService.likePost(likeDto.getContent());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<String>> likeComment(@RequestBody LikeDto likeDto) {
        ApiResponse<String> response = likeService.likeComment(likeDto.getContent());
        return new ResponseEntity<>(response, response.getStatus());
    }
}
