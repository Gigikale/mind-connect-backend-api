package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.GroupRequestDto;
import com.mindconnect.mindconnect.dtos.GroupResponseDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.GroupServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupServices groupServices;

    @PostMapping()
    public ResponseEntity<ApiResponse<GroupResponseDto>> createGroup(@RequestBody  GroupRequestDto requestDto){
        return groupServices.createGroup(requestDto);
    }
}
