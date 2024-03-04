package com.mindconnect.mindconnect.services;

import com.mindconnect.mindconnect.dtos.ExitGroupDto;
import com.mindconnect.mindconnect.dtos.GroupListDto;
import com.mindconnect.mindconnect.dtos.GroupRequestDto;
import com.mindconnect.mindconnect.dtos.GroupResponseDto;
import com.mindconnect.mindconnect.dtos.JoinGroupDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupServices {
    ResponseEntity<ApiResponse<GroupResponseDto>> createGroup(GroupRequestDto requestDto);
    ResponseEntity<List<GroupListDto>> getGroupsByPopularity(Integer page, Integer size);
    ResponseEntity<ApiResponse<String>> exitGroup(ExitGroupDto exitGroupDto);
    ResponseEntity<ApiResponse<String>> joinGroup(JoinGroupDto joinGroupDto);
}
