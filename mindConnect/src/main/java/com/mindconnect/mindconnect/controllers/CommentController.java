package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.CommentDTO;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createComment(@RequestBody CommentDTO commentDTO, @RequestParam("postId") String postId) {
        ApiResponse<String> apiResponse = commentService.createComment(commentDTO, postId).getBody();
        return ResponseEntity.ok(apiResponse);
    }
}
