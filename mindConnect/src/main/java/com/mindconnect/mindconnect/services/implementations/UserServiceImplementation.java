package com.mindconnect.mindconnect.services.implementations;


import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.ChangePasswordDto;
import com.mindconnect.mindconnect.dtos.ProfileDto;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.mapper.PostMapper;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.payloads.PostHistory;
import com.mindconnect.mindconnect.repositories.PostRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.UserService;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordDto changePasswordDto) {
            String email = UserUtil.getLoggedInUser();
            User user = userRepository.findByEmailAddress(email)
                    .orElseThrow(() -> new MindConnectException("User not found"));

            if (passwordEncoder.matches(changePasswordDto.oldPassword(), user.getPassword())) {
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
        user.setProfilePictureUrl(profileDto.profilePictureUrl());
        userRepository.save(user);

        ApiResponse<String> response = new ApiResponse<>(
                "Profile updated successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * @param user The user id to filter post by.
     * @param groupId The group id to filter post by.
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @returnAn ApiResponse containing the paginated list of PostHistory objects.
     */
    @Override
    public ApiResponse<List<PostHistory>> viewPosts(Boolean user, UUID groupId, Integer page, Integer size) {
        // Validating page and size parameters
        page = page != null && page >= 0 ? page : 0;
        size = size != null && size > 0 ? size : DEFAULT_PAGE_SIZE;

        // Setting up pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));

        if(user){
            String email = UserUtil.getLoggedInUser();
            User loggedInUser = userRepository.findByEmailAddress(email)
                    .orElseThrow(() -> new MindConnectException("User doesn't exist"));
            UUID userId = loggedInUser.getId();
            Page<Post> userPosts = postRepository.findByUserId(userId, pageable);
            return new ApiResponse<>(mapPostsToPostHistory(userPosts), "User's posts fetched successfully");

        } else if (groupId != null) {
            Page<Post> groupPosts = postRepository.findByGroupId(groupId, pageable);
            return new ApiResponse<>(mapPostsToPostHistory(groupPosts), "Group posts fetched successfully");

        } else {
            Page<Post> publicPosts = postRepository.findAllByHiddenFalse(pageable);
            return new ApiResponse<>(mapPostsToPostHistory(publicPosts), "Public posts fetched successfully");
        }

    }

    /**
     * Maps a Page of Post entities to a List of PostHistory objects.
     *
     * @param posts The Page object containing Post entities.
     * @return A list of PostHistory objects.
     */
    private List<PostHistory> mapPostsToPostHistory(Page<Post> posts){
       return posts.stream()
               .map(post -> PostMapper.mapToPostHistory(post, new PostHistory()))
               .collect(Collectors.toList());
    }
}