package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.ChatDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public ResponseEntity<ApiResponse<String>> processMessage(
            @Payload @NotNull ChatDto chatDto
    ) {
        System.out.println(chatDto);
        return chatMessageService.sendMessage(chatDto);
    }
}
