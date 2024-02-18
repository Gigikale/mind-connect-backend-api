package com.mindconnect.mindconnect.services.implementations;


import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.ChangePasswordDto;
import com.mindconnect.mindconnect.dtos.ProfileDto;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.UserService;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}