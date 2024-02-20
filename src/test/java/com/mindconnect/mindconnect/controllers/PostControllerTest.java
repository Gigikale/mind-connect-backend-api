package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.PostDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost_Successful() throws MindConnectException {
        PostDTO postDto = new PostDTO("Test content");
        ApiResponse<?> expectedResponse = new ApiResponse<>("Post created successfully", HttpStatus.OK);
        ResponseEntity<ApiResponse<String>> responseEntity = ResponseEntity.ok((ApiResponse<String>) expectedResponse);
        when(postService.createPost(postDto)).thenReturn(responseEntity);

        ResponseEntity<ApiResponse<String>> actualResponseEntity = postController.createPost(postDto);

        assertApiResponseEquals(expectedResponse, actualResponseEntity.getBody());
    }

    @Test
    void createPost_ExceptionThrown() throws MindConnectException {
        PostDTO postDto = new PostDTO("Test content");
        MindConnectException exception = new MindConnectException("Failed to create post");
        ApiResponse<?> expectedResponse = new ApiResponse<>("Failed to create post: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<ApiResponse<?>> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(expectedResponse);
        when(postService.createPost(postDto)).thenThrow(exception);

        ResponseEntity<ApiResponse<String>> actualResponseEntity = postController.createPost(postDto);

        assertApiResponseEquals(expectedResponse, actualResponseEntity.getBody());
    }

    private void assertApiResponseEquals(ApiResponse<?> expected, ApiResponse<?> actual) {
        assertEquals(expected.getMessage(), actual.getMessage());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}
