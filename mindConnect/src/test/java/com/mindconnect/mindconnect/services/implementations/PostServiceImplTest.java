package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.PostDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.exceptions.PostCreationFailedException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.PostRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");
    }

    @Test
    void createPost_Successful() throws MindConnectException {
        String email = "test@example.com";
        User user = new User();
        user.setEmailAddress(email);

        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        PostDTO postDto = new PostDTO("Test content");

        ResponseEntity<ApiResponse<String>> responseEntity = postService.createPost(postDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Post created successfully", responseEntity.getBody().getMessage());
    }

    @Test
    void createPost_UserNotFound() {
        String email = "test@example.com";

        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.empty());

        PostDTO postDto = new PostDTO("Test content");

        assertThrows(MindConnectException.class, () -> postService.createPost(postDto));
    }

    @Test
    void createPost_FailedPostCreation() {
        String email = "test@example.com";
        User user = new User();
        user.setEmailAddress(email);

        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(null);

        PostDTO postDto = new PostDTO("Test content");

        assertThrows(PostCreationFailedException.class, () -> postService.createPost(postDto));
    }
}

