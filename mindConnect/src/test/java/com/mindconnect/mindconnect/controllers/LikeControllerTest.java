package com.mindconnect.mindconnect.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindconnect.mindconnect.dtos.LikeDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.LikeService;
import com.mindconnect.mindconnect.services.implementations.JwtImplementation;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = LikeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class LikeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LikeService likeService;
    @MockBean
    private JwtImplementation implementation;

    LikeDto dto;

    @BeforeEach
    void setup() {
        dto = new LikeDto();
        dto.setContent(UUID.randomUUID());
    }

    @Test
    @SneakyThrows
    void likePostAndReturnStatusOk() {
        when(likeService.likePost(dto.getContent())).thenReturn(new ApiResponse<>(
                "Post liked",
                HttpStatus.OK
        ));
        ResultActions actions = mockMvc.perform(post("/like/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    void likePostAndReturnUserNotFound() {
        when(likeService.likePost(dto.getContent())).thenReturn(new ApiResponse<>(
                "User not found",
                HttpStatus.BAD_REQUEST
        ));
        ResultActions actions = mockMvc.perform(post("/like/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void likePostAndReturnPostNotFound() {
        when(likeService.likePost(dto.getContent())).thenReturn(new ApiResponse<>(
                "Post not found",
                HttpStatus.BAD_REQUEST
        ));
        ResultActions actions = mockMvc.perform(post("/like/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void unLikePost() {
        when(likeService.likePost(dto.getContent())).thenReturn(new ApiResponse<>(
                "Post unliked",
                HttpStatus.OK
        ));
        ResultActions actions = mockMvc.perform(post("/like/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    void likeCommentAndReturnStatusOk() {
        when(likeService.likeComment(dto.getContent())).thenReturn(new ApiResponse<>(
                "Comment liked",
                HttpStatus.OK
        ));
        ResultActions actions = mockMvc.perform(post("/like/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    void likeCommentAndReturnUserNotFound() {
        when(likeService.likeComment(dto.getContent())).thenReturn(new ApiResponse<>(
                "User not found",
                HttpStatus.BAD_REQUEST
        ));
        ResultActions actions = mockMvc.perform(post("/like/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void likeCommentAndReturnCommentNotFound() {
        when(likeService.likeComment(dto.getContent())).thenReturn(new ApiResponse<>(
                "Comment not found",
                HttpStatus.BAD_REQUEST
        ));
        ResultActions actions = mockMvc.perform(post("/like/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void unlikeComment() {
        when(likeService.likeComment(dto.getContent())).thenReturn(new ApiResponse<>(
                "Comment unliked",
                HttpStatus.OK
        ));
        ResultActions actions = mockMvc.perform(post("/like/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}