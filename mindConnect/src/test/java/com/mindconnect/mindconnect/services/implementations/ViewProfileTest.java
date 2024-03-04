package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.dtos.ProfileResponse;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

class ViewProfileTest {


    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    @Test
    void testViewProfile() {
        // Mock the user repository
        ProfileResponse user = new ProfileResponse();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setState("California");
        user.setCountry("USA");
        user.setUsername("johndoe");
        user.setMentalCondition("Healthy");
        user.setGender("Male");
        user.setProfilePicture("profile-picture-url");


        // Call the viewProfile method
        ResponseEntity<ApiResponse<ProfileResponse>> responseEntity = userServiceImplementation.viewProfile();

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ApiResponse<ProfileResponse> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Request Processed Successfully", response.getMessage());

        ProfileResponse profileResponse = response.getData();
        assertNotNull(profileResponse);
        assertEquals("John", profileResponse.getFirstName());
        assertEquals("Doe", profileResponse.getLastName());
        assertEquals("California", profileResponse.getState());
        assertEquals("USA", profileResponse.getCountry());
        assertEquals("johndoe", profileResponse.getUsername());
        assertEquals("Healthy", profileResponse.getMentalCondition());
        assertEquals("Male", profileResponse.getGender());
        assertEquals("profile-picture-url", profileResponse.getProfilePicture());
    }
}