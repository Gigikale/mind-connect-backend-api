package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.Comment;
import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.dtos.CommentDTO;
import com.mindconnect.mindconnect.exceptions.CommentCreationFailedException;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.CommentRepository;
import com.mindconnect.mindconnect.repositories.PostRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.CommentService;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Override
    public ResponseEntity<ApiResponse<String>> createComment(CommentDTO commentDto, String postId) throws MindConnectException {
        String email = UserUtil.getLoggedInUser();
        var user = userRepository.findByEmailAddress(email).orElseThrow(
                () -> new MindConnectException("User not found")
        );

        Post post = postRepository.findById(UUID.fromString(postId)).orElseThrow(
                () -> new MindConnectException("Post not found")
        );

        Comment comment = new Comment();
        comment.setContent(commentDto.content());
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        if (savedComment == null) {
            throw new CommentCreationFailedException("Failed to create comment");
        }

        return ResponseEntity.ok(new ApiResponse<>("Comment created successfully", HttpStatus.OK));
    }

}
