package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.GroupRequestDto;
import com.mindconnect.mindconnect.dtos.GroupResponseDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface GroupServices {
    ResponseEntity<ApiResponse<GroupResponseDto>> createGroup(GroupRequestDto requestDto);
}
