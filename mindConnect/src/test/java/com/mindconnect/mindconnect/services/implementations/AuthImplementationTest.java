package com.mindconnect.mindconnect.services.implementations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.LoginDto;
import com.mindconnect.mindconnect.dtos.LoginResponse;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthImplementation.class})
@ExtendWith(SpringExtension.class)
class AuthImplementationTest {
    @Autowired
    private AuthImplementation authImplementation;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtImplementation jwtImplementation;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private User user;

    @BeforeEach
    public void setup() {
        user = new User("onyeka"
                , "igwe"
                , "iLoveYou"
                ,"igweonyeka6@gmail.com"
                ,"Nigeria"
                ,"Edo"
                ,"Male"
                ,"Okay"
                ,"igwe6"
                ,"null"
                , LocalDateTime.now()
                ,null
                ,null
                ,null
                ,null); ;
    }

    @Test
    public void testLogin_Successful() {
        LoginDto loginDto = new LoginDto("igweonyeka6@gmail.com", "iLoveYou");

        String jwtToken = "jwtToken";

        when(userRepository.findByEmailAddress(loginDto.getEmailAddress())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(jwtImplementation.generateToken(user)).thenReturn(jwtToken);

        ResponseEntity<ApiResponse<LoginResponse>> responseEntity = authImplementation.login(loginDto);
        // Assert
        assertEquals(HttpStatus.OK, ((ResponseEntity<?>) responseEntity).getStatusCode());
        ApiResponse<LoginResponse> response = responseEntity.getBody();
        assertEquals("Login successful", response.getMessage());
        LoginResponse loginResponse = response.getData();
        assertEquals(user.getFirstName(), loginResponse.firstName());
        assertEquals(user.getLastName(), loginResponse.lastName());
        assertEquals(jwtToken, loginResponse.token());

        verify(userRepository, times(1)).findByEmailAddress(loginDto.getEmailAddress());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtImplementation, times(1)).generateToken(user);
    }

    @Test
    public void testLogin_UserNotFound() {
        LoginDto loginDto = new LoginDto("igweonyeka6@gmail.com", "iLoveYou");

        when(userRepository.findByEmailAddress(loginDto.getEmailAddress())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<LoginResponse>> responseEntity = authImplementation.login(loginDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ApiResponse<LoginResponse> response = responseEntity.getBody();
        assertEquals("User wasn't found", response.getMessage());
        assertNull(response.getData());

        verify(userRepository, times(1)).findByEmailAddress(loginDto.getEmailAddress());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtImplementation, never()).generateToken(any());
    }
}
