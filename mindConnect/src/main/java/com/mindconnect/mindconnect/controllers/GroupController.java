package com.mindconnect.mindconnect.controllers;

import com.mindconnect.mindconnect.dtos.ExitGroupDto;
import com.mindconnect.mindconnect.dtos.GroupListDto;
import com.mindconnect.mindconnect.dtos.GroupRequestDto;
import com.mindconnect.mindconnect.dtos.GroupResponseDto;
import com.mindconnect.mindconnect.dtos.JoinGroupDto;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.services.GroupServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupServices groupServices;

    @PostMapping("/create-group")
    public ResponseEntity<ApiResponse<GroupResponseDto>> createGroup(@RequestBody  GroupRequestDto requestDto){
        return groupServices.createGroup(requestDto);
    }
    @GetMapping("/popular_groups")
    public ResponseEntity<List<GroupListDto>> getGroupsByPopularity(Integer page, Integer size) {
        return groupServices.getGroupsByPopularity(page, size);
    }
    @DeleteMapping("/exitGroup")
    public ResponseEntity<ApiResponse<String>> exitGroup(ExitGroupDto exitGroupDto){
        return groupServices.exitGroup(exitGroupDto);
    }
    @PostMapping("/joinGroup")
    public ResponseEntity<ApiResponse<String>> joinGroup(JoinGroupDto joinGroupDto){
        return groupServices.joinGroup(joinGroupDto);
    }
}
