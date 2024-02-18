package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.ChangePasswordDto;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Unit tests for the UserServiceImplementation class.
 * These tests cover various scenarios related to changing user passwords.
 */
class UserServiceImplementationTest {

    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    /**
     * Setup method executed before each test case.
     * Initializes the mocks and sets up a mock security context.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken("Naz@gmail.com", "naztarr$");
        securityContext.setAuthentication(authentication);
    }

    /**
     * Method to create a mock User instance.
     * @return A mock User instance.
     */
    private User createUser() {
        User user = new User();
        user.setEmailAddress("Naz@gmail.com");
        user.setPassword(passwordEncoder.encode("naztarr$"));
        return user;
    }

    /**
     * Test case for successful password change.
     * It verifies that the password is changed successfully when the old password is correct.
     */
    @Test
    void testChangePassword_Successful() {
        // Mocking
        String userEmail = "Naz@gmail.com";
        String oldPassword = "naztarr$";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";

        ChangePasswordDto changePasswordDto = new ChangePasswordDto(oldPassword, newPassword, confirmPassword);

        User mockUser = createUser();

        when(userRepository.findByEmailAddress(userEmail)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(oldPassword, mockUser.getPassword())).thenReturn(true);

        // Test
        ResponseEntity<ApiResponse<String>> responseEntity = userServiceImplementation.changePassword(changePasswordDto);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getBody().getStatus());
        assertNotNull(responseEntity.getBody());
        assertEquals("New password", responseEntity.getBody().getData());
        assertEquals("Password changed successfully", responseEntity.getBody().getMessage());

        // Verification
        verify(userRepository).findByEmailAddress(userEmail);
        verify(passwordEncoder).encode(newPassword);
        verify(userRepository, times(1)).save(mockUser);
    }

    /**
     * Test case for handling user not found scenario.
     * It verifies that a MindConnectException is thrown when the user is not found.
     */
    @Test
    void testChangePassword_UserNotFound() {
        // Mocking
        String userEmail = "Naz@gmail.com";
        String oldPassword = "naztarr$";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";


        ChangePasswordDto changePasswordDto = new ChangePasswordDto(oldPassword, newPassword, confirmPassword);
        User mockUser = createUser();


        when(userRepository.findByEmailAddress(userEmail)).thenThrow(new MindConnectException("User not found"));

        // Test and assertions
        MindConnectException exception = assertThrows(MindConnectException.class,
                () -> userServiceImplementation.changePassword(changePasswordDto));
        assertEquals("User not found", exception.getMessage());

        // Verification
        verify(userRepository).findByEmailAddress(userEmail);
        verify(userRepository, never()).save(mockUser);
    }

    /**
     * Test case for handling incorrect old password scenario.
     * It verifies that a MindConnectException is thrown when the old password is incorrect.
     */
    @Test
    void testChangePassword_IncorrectOldPassword() {
        // Mocking
        String userEmail = "Naz@gmail.com";
        String incorrectOldPassword = "incorrectOldPassword";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";

        ChangePasswordDto changePasswordDto = new ChangePasswordDto(incorrectOldPassword, newPassword, confirmPassword);

        User mockUser = createUser();

        when(userRepository.findByEmailAddress(userEmail)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(incorrectOldPassword, mockUser.getPassword())).thenReturn(false);

        // Test and assertions
        MindConnectException exception = assertThrows(MindConnectException.class,
                () -> userServiceImplementation.changePassword(changePasswordDto));
        assertEquals("Incorrect old password", exception.getMessage());


        // Verification
        verify(userRepository).findByEmailAddress(userEmail);
        verify(passwordEncoder).matches(incorrectOldPassword, mockUser.getPassword());
    }

    /**
     * Test case for handling new passwords not matching scenario.
     * It verifies that a MindConnectException is thrown when the new passwords do not match.
     */
    @Test
    void testChangePassword_NewPasswordsNotMatching() {
        // Mocking
        String userEmail = "Naz@gmail.com";
        String oldPassword = "naztarr$";
        String newPassword = "newPassword";
        String confirmPassword = "differentPassword";

        ChangePasswordDto changePasswordDto = new ChangePasswordDto(oldPassword, newPassword, confirmPassword);

        User mockUser = createUser();

        when(UserUtil.getLoggedInUser()).thenReturn(userEmail);
        when(userRepository.findByEmailAddress(userEmail)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(oldPassword, mockUser.getPassword())).thenReturn(true);

        // Test and assertions
        MindConnectException exception = assertThrows(MindConnectException.class,
                () -> userServiceImplementation.changePassword(changePasswordDto));
        assertEquals("New passwords do not match", exception.getMessage());

        // Verification
        verify(userRepository).findByEmailAddress(userEmail);
        verify(passwordEncoder).matches(oldPassword, mockUser.getPassword());
        verify(userRepository, never()).save(mockUser);
    }
}