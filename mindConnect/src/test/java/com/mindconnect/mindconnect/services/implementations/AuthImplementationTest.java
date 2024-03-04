package com.mindconnect.mindconnect.services.implementations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.LoginDto;
import com.mindconnect.mindconnect.dtos.LoginResponse;
import com.mindconnect.mindconnect.dtos.ResetPasswordDto;
import com.mindconnect.mindconnect.enums.AccountStatus;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @MockBean
    private AuthImplementation authImplementation;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtImplementation jwtImplementation;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
       user = new User("onyeka",
               "igwe"
                ,"iLoveYou"
               ,null
               ,"igweonyeka@gmail.com"
                ,"Nigeria"
                ,"Edo"
                ,"Male"
                ,"Okay"
                ,"igwe6"
                ,null
                ,LocalDateTime.now()
               , AccountStatus.ACTIVE
                ,null
                ,null
               ,false
                ,null
                ,null);
    }

    @Test
    public void testLogin_Successful() {
        LoginDto loginDto = new LoginDto("igweonyeka6@gmail.com", "iLoveYou");

        String jwtToken = "jwtToken";

        when(userRepository.findByEmailAddress(loginDto.emailAddress())).thenReturn(Optional.of(user));
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

        verify(userRepository, times(1)).findByEmailAddress(loginDto.emailAddress());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtImplementation, times(1)).generateToken(user);
    }

    @Test
    public void testLogin_UserNotFound() {
        LoginDto loginDto = new LoginDto("igweonyeka6@gmail.com", "iLoveYou");

        when(userRepository.findByEmailAddress(loginDto.emailAddress())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<LoginResponse>> responseEntity = authImplementation.login(loginDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ApiResponse<LoginResponse> response = responseEntity.getBody();
        assertEquals("User wasn't found", response.getMessage());
        assertNull(response.getData());

        verify(userRepository, times(1)).findByEmailAddress(loginDto.emailAddress());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtImplementation, never()).generateToken(any());
    }

    @Test
    public void testResetPassword() {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto("@Luweezy360#", "Qwerty12345" );

    }

    @Test

    void resetPassword_Success() {
        String token = "123token";
        String password = "louis123";
        String confirmPassword = "louis123";

        when(jwtImplementation.extractUsername(token)).thenReturn(user.getEmailAddress());
        when(jwtImplementation.isTokenExpired(token)).thenReturn(false);
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse<String>> responseEntity = authImplementation.resetPassword(token, password, confirmPassword);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password changed for igweonyeka6@gmail.com", responseEntity.getBody().getMessage());
        assertEquals("Successfully changed password", responseEntity.getBody().getData());
        assertFalse(user.getPasswordRecovery());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void resetPassword_ExpiredToken() {
        String token = "token124";
        String password = "luwiLoiz";
        String confirmPassword = "luwiLoiz";

        when(jwtImplementation.extractUsername(token)).thenReturn(user.getEmailAddress());
        when(jwtImplementation.isTokenExpired(token)).thenReturn(true);

        MindConnectException exception = assertThrows(MindConnectException.class,
                () -> authImplementation.resetPassword(token, password, confirmPassword));

        assertEquals("Link has expired. Please request for a new link.", exception.getMessage());
        verify(userRepository, never()).findByEmailAddress(anyString());
    }

    @Test
    void resetPassword_PasswordMismatch() {
        String token = "invalidToken";
        String password = "luwiz";
        String confirmPassword = "lordLuwiz";

        when(jwtImplementation.extractUsername(token)).thenReturn(user.getEmailAddress());
        when(jwtImplementation.isTokenExpired(token)).thenReturn(false);
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.of(user));

        MindConnectException exception = assertThrows(MindConnectException.class,
                () -> authImplementation.resetPassword(token, password, confirmPassword));

        assertEquals("Password does not match", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void resetPassword_UserNotFound() {
        String token = "token404";
        String password = "badManLuiz";
        String confirmPassword = "badManLuiz";

        when(jwtImplementation.extractUsername(token)).thenReturn(user.getEmailAddress());
        when(jwtImplementation.isTokenExpired(token)).thenReturn(false);
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenThrow(new MindConnectException("User not found"));

        MindConnectException exception = assertThrows(MindConnectException.class,
                () -> authImplementation.resetPassword(token, password, confirmPassword));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void resetPassword_InvalidTokenFormat() {
        String token = "totoku";
        String password = "badManLuiz";
        String confirmPassword = "badManLuiz";

        when(jwtImplementation.extractUsername(token)).thenReturn(null);

        MindConnectException exception = assertThrows(MindConnectException.class,
                () -> authImplementation.resetPassword(token, password, confirmPassword));

        assertEquals("Link is not properly formatted.", exception.getMessage());
        verify(userRepository, never()).findByEmailAddress(anyString());
    }
}
