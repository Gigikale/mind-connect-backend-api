package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.dtos.PostDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.exceptions.PostCreationFailedException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.PostRepository;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.PostService;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<String>> createPost(PostDTO postDto) throws MindConnectException {
        String email = UserUtil.getLoggedInUser();
        var user = userRepository.findByEmailAddress(email).orElseThrow(
                () -> new MindConnectException("User not found")
        );

        Post post = new Post();
        post.setContent(postDto.content());
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        if (savedPost == null) {
            throw new PostCreationFailedException("Failed to create post");
        }

        return ResponseEntity.ok(new ApiResponse<>("Post created successfully", HttpStatus.OK));
    }

}
