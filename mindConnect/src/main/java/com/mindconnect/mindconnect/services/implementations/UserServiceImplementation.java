package com.mindconnect.mindconnect.services.implementations;


import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.ChangePasswordDto;
import com.mindconnect.mindconnect.dtos.ProfileDto;
import com.mindconnect.mindconnect.dtos.ProfileResponse;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.mapper.PostMapper;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.payloads.PostHistory;
import com.mindconnect.mindconnect.repositories.LikeRepository;
import com.mindconnect.mindconnect.repositories.PostRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.UserService;
import com.mindconnect.mindconnect.utils.UploadPhoto;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordDto changePasswordDto) {
            String email = UserUtil.getLoggedInUser();
            User user = userRepository.findByEmailAddress(email)
                    .orElseThrow(() -> new MindConnectException("User not found"));

            if (passwordEncoder.matches(changePasswordDto.oldPassword(), user.getPassword())) {
                if(!changePasswordDto.newPassword().equals(changePasswordDto.oldPassword())){
                    if (changePasswordDto.newPassword().equals(changePasswordDto.confirmPassword())) {
                        user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
                        userRepository.save(user);

                        ApiResponse<String> response = new ApiResponse<>(
                                "New password",
                                "Password changed successfully"
                        );
                        return new ResponseEntity<>(response, response.getStatus());
                    } else {
                        throw new MindConnectException("New passwords do not match");
                    }
                } else {
                    throw new MindConnectException("New and old passwords are the same");
                }
            } else {
                throw new MindConnectException("Incorrect old password");
            }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> editProfile(ProfileDto profileDto) {
        String email = UserUtil.getLoggedInUser();
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new MindConnectException("User not found"));
        user.setUsername(profileDto.username());
        user.setFirstName(profileDto.firstName());
        user.setLastName(profileDto.lastName());
        user.setGender(profileDto.gender());
        user.setCountry(profileDto.country());
        user.setState(profileDto.state());
        user.setMentalCondition(profileDto.mentalCondition());
        userRepository.save(user);

        ApiResponse<String> response = new ApiResponse<>(
                "Profile updated successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * @param isUser The user id to filter post by.
     * @param groupId The group id to filter post by.
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @returnAn ApiResponse containing the paginated list of PostHistory objects.
     */
    @Override
    public ApiResponse<List<PostHistory>> viewPosts(Boolean isUser, UUID groupId, Integer page, Integer size) {
        page = page != null && page >= 0 ? page : 0;
        size = size != null && size > 0 ? size : DEFAULT_PAGE_SIZE;

        Pageable pageable = PageRequest.of(page, size);

        if(isUser){
            User loggedInUser = userRepository.findByEmailAddress(UserUtil.getLoggedInUser())
                    .orElseThrow(() -> new MindConnectException("User doesn't exist"));
            UUID userId = loggedInUser.getId();
            Page<Post> userPosts = postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
            return new ApiResponse<>(mapPostsToPostHistory(userPosts), "User's posts fetched successfully");

        } else if (groupId != null) {
            Page<Post> groupPosts = postRepository.findByGroupIdOrderByCreatedAtDesc(groupId, pageable);
            return new ApiResponse<>(mapPostsToPostHistory(groupPosts), "Group posts fetched successfully");

        } else {
            Page<Post> publicPosts = postRepository.findAllByHiddenFalseAndGroupIsNullOrderByCreatedAtDesc(pageable);
            return new ApiResponse<>(mapPostsToPostHistory(publicPosts), "Public posts fetched successfully");
        }

    }

    @Override
    public ResponseEntity<ApiResponse<String>> updateProfilePic(String filePath) throws IOException {
        String email = UserUtil.getLoggedInUser();
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new MindConnectException("User not found"));
        String uri = UploadPhoto.upload(filePath);
        user.setProfilePictureUrl(uri);
        userRepository.save(user);
        ApiResponse<String> response = new ApiResponse<>(
                uri,
                "Profile pic updated successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Maps a Page of Post entities to a List of PostHistory objects.
     *
     * @param posts The Page object containing Post entities.
     * @return A list of PostHistory objects.
     */
    private List<PostHistory> mapPostsToPostHistory(Page<Post> posts){
       return posts.stream()
               .map(post -> PostMapper
                       .mapToPostHistory(post,
                               new PostHistory(),
                               likeRepository.getLikeCountByPostId(post.getId())))
               .collect(Collectors.toList());
    }
    public ResponseEntity<ApiResponse<ProfileResponse>> viewProfile() {
        var user = userRepository.findByEmailAddress(UserUtil.getLoggedInUser()).orElseThrow(()->
                new MindConnectException("User not found"));
            ProfileResponse profileResponse = new ProfileResponse();
            profileResponse.setFirstName(user.getFirstName());
            profileResponse.setLastName(user.getLastName());
            profileResponse.setState(user.getState());
            profileResponse.setCountry(user.getCountry());
            profileResponse.setUsername(user.getUsername());
            profileResponse.setEmailAddress(user.getEmailAddress());
            profileResponse.setMentalCondition(user.getMentalCondition());
            profileResponse.setGender(user.getGender());
            profileResponse.setProfilePicture(user.getProfilePictureUrl());
            ApiResponse response = new ApiResponse<>(profileResponse, "Request Processed Successfully");

            return new ResponseEntity<>(response, response.getStatus());
        }
    }
