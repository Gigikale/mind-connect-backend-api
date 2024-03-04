package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.Comment;
import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.CommentDTO;
import com.mindconnect.mindconnect.exceptions.CommentCreationFailedException;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.CommentRepository;
import com.mindconnect.mindconnect.repositories.PostRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.utils.UserUtil;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

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
    void createComment_Successful() throws MindConnectException {
        String email = "test@example.com";
        String postId = "ec440a9f-eebf-4725-8964-fd97f9165fef";
        CommentDTO commentDto = new CommentDTO("Test comment");
        Post post = new Post();
        post.setId(UUID.fromString(postId));

        when(UserUtil.getLoggedInUser()).thenReturn(email);
        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.of(new User()));
        when(postRepository.findById(UUID.fromString(postId))).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment());

        ResponseEntity<ApiResponse<String>> responseEntity = commentService.createComment(commentDto, postId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Comment created successfully", responseEntity.getBody().getMessage());
    }

    @Test
    void createComment_UserNotFound() {
        String postId = "ec440a9f-eebf-4725-8964-fd97f9165fef";
        CommentDTO commentDto = new CommentDTO("Test comment");

        when(UserUtil.getLoggedInUser()).thenReturn("test@example.com");
        when(userRepository.findByEmailAddress(anyString())).thenReturn(Optional.empty());

        assertThrows(MindConnectException.class, () -> commentService.createComment(commentDto, postId));
    }

    @Test
    void createComment_PostNotFound() {
        String email = "test@example.com";
        String postId = "ec440a9f-eebf-4725-8964-fd97f9165fef";
        CommentDTO commentDto = new CommentDTO("Test comment");

        when(UserUtil.getLoggedInUser()).thenReturn(email);
        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.of(new User()));
        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(MindConnectException.class, () -> commentService.createComment(commentDto, postId));
    }

    @Test
    void createComment_FailedCommentCreation() {
        String email = "test@example.com";
        String postId = "ec440a9f-eebf-4725-8964-fd97f9165fef";
        CommentDTO commentDto = new CommentDTO("Test comment");
        Post post = new Post();
        post.setId(UUID.fromString(postId));

        when(UserUtil.getLoggedInUser()).thenReturn(email);
        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.of(new User()));
        when(postRepository.findById(UUID.fromString(postId))).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenReturn(null);

        assertThrows(CommentCreationFailedException.class, () -> commentService.createComment(commentDto, postId));
    }
}

