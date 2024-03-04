package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.Group;
import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.ExitGroupDto;
import com.mindconnect.mindconnect.dtos.GroupListDto;
import com.mindconnect.mindconnect.dtos.GroupRequestDto;
import com.mindconnect.mindconnect.dtos.GroupResponseDto;
import com.mindconnect.mindconnect.dtos.JoinGroupDto;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.GroupRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.GroupServices;
import com.mindconnect.mindconnect.utils.FormatTimeAgo;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@ComponentScan
public class GroupServiceImpl implements GroupServices {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<GroupResponseDto>> createGroup(GroupRequestDto requestDto) {

        var email = UserUtil.getLoggedInUser();
        var userAdmin = userRepository.findByEmailAddress(email).orElseThrow(()-> new MindConnectException("User not found"));
        var groupNameAlreadyExists = groupRepository.existsByNameIgnoreCase(requestDto.name());

        if (groupNameAlreadyExists){
            throw new MindConnectException("Group name already exists, please change group name");
        }
        Group newGroup = Group.builder()
                .name(requestDto.name())
                .about(requestDto.about())
                .admin(userAdmin)
                .build();
        Set<User> groupUsers = new HashSet<>();
        groupUsers.add(userAdmin);
        newGroup.setUsers(groupUsers);
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
        @Override
        public ResponseEntity<List<GroupListDto>> getGroupsByPopularity(Integer pageNumber, Integer sizeNumber) {
            Pageable pageable = PageRequest.of(pageNumber, sizeNumber);
            Page<Object[]> sqlQuery = groupRepository.getGroupList(pageable);
            List<GroupListDto> groupListDto = new ArrayList<>();
            for(Object[] objects:sqlQuery) {
                GroupListDto groupDetails = new GroupListDto();
                groupDetails.setName((String) objects [0]);
                groupDetails.setAbout((String) objects [1]);
                groupDetails.setUsersCount((Long) objects [2]);
                groupDetails.setPostsCount((Long) objects [3]);
                groupDetails.setAdminName((String) objects [4]);
                Timestamp timestamp = (Timestamp) objects [5];
                groupDetails.setCreatedAt(FormatTimeAgo.formatRelativeTime(timestamp.toLocalDateTime()));
                groupListDto.add(groupDetails);
            }
        return ResponseEntity.ok(groupListDto);
    }
    public ResponseEntity<ApiResponse<String>> exitGroup(ExitGroupDto exitGroupDto) {
        var user = userRepository.findByEmailAddress(UserUtil.getLoggedInUser()).orElseThrow(() -> new MindConnectException("User not found"));
        var existingGroup = groupRepository.findByNameIgnoreCase(exitGroupDto.name());
        if (existingGroup.isPresent()){
            Group group = existingGroup.get();
            Set<User> groupMembers = group.getUsers();
            boolean isMember = false;
            for (User member : groupMembers){
                if (member.getEmailAddress().equals(user.getEmailAddress())){
                    isMember = true;
                    break;
                }
            }
            if (!isMember) {
                throw new MindConnectException("User is not a member of the group or has already exited the group");
            }
            group.getUsers().remove(user);
            groupRepository.save(group);
            return ResponseEntity.ok(new ApiResponse<>("User exited group", HttpStatus.OK));
        }
        throw new MindConnectException("group "+ exitGroupDto.name() + " not found");
    }


    @Override
    public ResponseEntity<ApiResponse<String>> joinGroup(JoinGroupDto joinGroupDto) {
        var user = userRepository.findByEmailAddress(UserUtil.getLoggedInUser()).orElseThrow(() -> new MindConnectException("User not found"));
        var existingGroup = groupRepository.findByNameIgnoreCase(joinGroupDto.name());
        UUID userId = user.getId();
        if (existingGroup.isPresent()){
            var emailAddress = existingGroup.get().getAdmin().getEmailAddress();
            Group group = existingGroup.get();
            Set<User> groupmembers = group.getUsers();
            if (user.getEmailAddress().equals(emailAddress)){
                throw new MindConnectException("User is an admin");
            }
            for (User member : groupmembers){
                if (member.getId().equals(userId)){
                    throw new MindConnectException("User already a member");
                }
            }
            groupmembers.add(user);
            group.setUsers(groupmembers);
            group.getPosts();
            groupRepository.save(group);
            ApiResponse<String> response = new ApiResponse<>("Successfully joined group",HttpStatus.OK);
            return new ResponseEntity<>(response,response.getStatus());
        }

        throw new MindConnectException("Group doesn't exist");
    }

}
