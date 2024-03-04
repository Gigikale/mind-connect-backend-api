package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.ChatDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChatMessageService {
    ResponseEntity<ApiResponse<String>> sendMessage(ChatDto chatDto);
}
