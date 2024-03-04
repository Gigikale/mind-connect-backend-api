package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.dtos.PostDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createPost(@RequestBody PostDTO postDto) {
        ApiResponse<String> apiResponse = postService.createPost(postDto).getBody();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{postId}/report")
    public ResponseEntity<ApiResponse<String>> reportPost(@PathVariable UUID postId, @RequestParam String reportReason) throws MindConnectException {
        ApiResponse<String> apiResponse = postService.reportPost(postId, reportReason).getBody();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{postId}/update")
    public ResponseEntity<ApiResponse<String>> updatePost(@PathVariable UUID postId, @RequestBody PostDTO postDTO) {
        ApiResponse<String> updatedPost = postService.updatePost(postId, postDTO).getBody();
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable UUID postId) {
        ApiResponse<String> deletedPost = postService.deletePost(postId).getBody();
        return ResponseEntity.ok(deletedPost);
    }
}

