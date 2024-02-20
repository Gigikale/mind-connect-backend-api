package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.CommentDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComment_Successful() throws MindConnectException {
        CommentDTO commentDto = new CommentDTO("Test content");
        String postId = "postId";
        ApiResponse<String> expectedResponse = new ApiResponse<>("Comment created successfully", HttpStatus.OK);
        ResponseEntity<ApiResponse<String>> responseEntity = ResponseEntity.ok(expectedResponse);
        when(commentService.createComment(commentDto, postId)).thenReturn(responseEntity);

        ResponseEntity<ApiResponse<String>> actualResponseEntity = commentController.createComment(commentDto, postId);

        assertEquals(expectedResponse.getMessage(), actualResponseEntity.getBody().getMessage());
        assertEquals(expectedResponse.getStatus(), actualResponseEntity.getBody().getStatus());
    }

}
