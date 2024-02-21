package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.Group;
import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.GroupRequestDto;
import com.mindconnect.mindconnect.dtos.GroupResponseDto;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.GroupRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.GroupServices;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupServices {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<ApiResponse<GroupResponseDto>> createGroup(GroupRequestDto requestDto) {
        var email = UserUtil.getLoggedInUser();
        var userAdmin = userRepository.findByEmailAddress(email).orElseThrow(()-> new MindConnectException("User not found"));
        var group = groupRepository.existsByNameIgnoreCase(requestDto.name());

        if (group == true){
            throw new MindConnectException("Group name already exists, please change group name");
        }
        Group newGroup = Group.builder()
                .name(requestDto.name())
                .about(requestDto.about())
                .admin(userAdmin)
                .build();
        groupRepository.save(newGroup);
        userAdmin.getGroups().add(newGroup);
        userRepository.save(userAdmin);

        GroupResponseDto groupResponse = new GroupResponseDto(
                requestDto.name(),
                requestDto.about()
        );
        ApiResponse response = new ApiResponse<>(
                groupResponse,
                "Group created successfully"
        );
        return new ResponseEntity<>(response, response.getStatus());
    }
}
