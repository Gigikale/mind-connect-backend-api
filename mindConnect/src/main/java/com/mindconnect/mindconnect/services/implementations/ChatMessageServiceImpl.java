package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.ChatMessage;
import com.mindconnect.mindconnect.dtos.ChatDto;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.ChatMessageRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.ChatMessageService;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public ResponseEntity<ApiResponse<String>> sendMessage(ChatDto chatDto) {

        if((userRepository.findById(UUID.fromString(chatDto.recipientId())) == null)) {
            ApiResponse<String> response = new ApiResponse<>(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
            return new ResponseEntity<>(response.getStatus());
        }

        ChatMessage message = new ChatMessage();

        simpMessagingTemplate.convertAndSendToUser(
                chatDto.recipientId(),
                "/messages",
                chatDto.message()
        );
        message.setMessage(chatDto.message());
        message.setReceiver(userRepository.findById(UUID.fromString(chatDto.recipientId())));
        message.setSender(userRepository.findById(UUID.fromString(chatDto.senderId())));
        chatMessageRepository.save(message);

        ApiResponse<String> response = new ApiResponse<>(
                message.toString(),
                "Success",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response.getStatus());
    }
}
