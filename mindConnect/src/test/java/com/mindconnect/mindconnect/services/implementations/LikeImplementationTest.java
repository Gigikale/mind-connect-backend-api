package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.Comment;
import com.mindconnect.mindconnect.Models.Like;
import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.CommentRepository;
import com.mindconnect.mindconnect.repositories.LikeRepository;
import com.mindconnect.mindconnect.repositories.PostRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * For the purpose of this test, I am going to use the triple A method:
 * Arrange: Make the necessary arrangement for what I want to test for
 * Act: Act on the arranged items
 * Assert: Make certain assertions on what needs to be done
 */
@ExtendWith(MockitoExtension.class)
class LikeImplementationTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private LikeImplementation likeService;

    Post post;
    User user;
    Comment comment;
    Like like;
    SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .emailAddress("evaristus.adimonyemma@decagon.dev")
                .firstName("Evaristus")
                .lastName("Adimonyemma")
                .country("Nigeria")
                .gender("Male")
                .state("FCT - Abuja")
                .username("iamevaristus")
                .Password("iamevaristus")
                .build();
        user.setId(UUID.randomUUID());

        post = new Post();
        post.setId(UUID.randomUUID());
        post.setUser(user);
        post.setContent("Hello test");

        comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setUser(user);
        comment.setPost(post);

        like = new Like();

        securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmailAddress(), user.getPassword()
        );
        securityContext.setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(user);
        securityContext.setAuthentication(null);
    }

    @Test
    void likePostIfExists() {
        when(userRepository.findByEmailAddress(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.ofNullable(post));
        when(likeRepository.findByUser_IdAndPost_Id(Mockito.any(UUID.class), Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());
        when(likeRepository.save(Mockito.any(Like.class))).thenReturn(like);
        ApiResponse<String> response = likeService.likePost(post.getId());
        assertEquals("Post liked", response.getMessage());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void throwPostNotFoundExceptionWhenLikingPost() {
        when(userRepository.findByEmailAddress(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(post.getId())).thenThrow(new MindConnectException("Post not found"));
        MindConnectException exception = Assertions.assertThrows(
                MindConnectException.class,
                () -> likeService.likePost(post.getId())
        );
        Assertions.assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void throwUserNotFoundExceptionWhenLikingPost() {
        when(userRepository.findByEmailAddress(Mockito.anyString()))
                .thenThrow(new MindConnectException("User not found"));
        MindConnectException exception = Assertions.assertThrows(
                MindConnectException.class,
                () -> likeService.likePost(post.getId())
        );
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    void unLikePost() {
        when(userRepository.findByEmailAddress(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.ofNullable(post));
        like.setComment(null);
        like.setUser(user);
        like.setPost(post);

        when(likeRepository.findByUser_IdAndPost_Id(user.getId(), post.getId()))
                .thenReturn(Optional.ofNullable(like));
        likeRepository.delete(Mockito.any(Like.class));
        ApiResponse<String> response = likeService.likePost(post.getId());
        assertEquals("Post unliked", response.getMessage());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void likeCommentIfExists() {
        when(userRepository.findByEmailAddress(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(commentRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.ofNullable(comment));
        when(likeRepository.findByUser_IdAndComment_Id(Mockito.any(UUID.class), Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());
        when(likeRepository.save(Mockito.any(Like.class))).thenReturn(like);
        ApiResponse<String> response = likeService.likeComment(comment.getId());
        assertEquals("Comment liked", response.getMessage());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void throwCommentNotFoundExceptionWhenLikingComment() {
        when(userRepository.findByEmailAddress(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(commentRepository.findById(comment.getId())).thenThrow(new MindConnectException("Comment not found"));
        MindConnectException exception = Assertions.assertThrows(
                MindConnectException.class,
                () -> likeService.likeComment(comment.getId())
        );
        Assertions.assertEquals("Comment not found", exception.getMessage());
    }

    @Test
    void throwUserNotFoundExceptionWhenLikingComment() {
        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenThrow(new MindConnectException("User not found"));
        MindConnectException exception = Assertions.assertThrows(
                MindConnectException.class,
                () -> likeService.likeComment(comment.getId())
        );
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    void throwCommentLikedExceptionWhenLikingPost() {
        when(userRepository.findByEmailAddress(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(commentRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.ofNullable(comment));
        like.setComment(comment);
        like.setUser(user);
        like.setPost(null);

        when(likeRepository.findByUser_IdAndComment_Id(user.getId(), comment.getId()))
                .thenReturn(Optional.ofNullable(like));
        likeRepository.delete(Mockito.any(Like.class));
        ApiResponse<String> response = likeService.likeComment(comment.getId());
        assertEquals("Comment unliked", response.getMessage());
        assertEquals(HttpStatus.OK, response.getStatus());
    }
}