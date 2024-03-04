package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.Models.Report;
import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.PostDTO;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.exceptions.PostCreationFailedException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.*;
import com.mindconnect.mindconnect.services.PostService;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

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

        return ResponseEntity.ok(new ApiResponse<>("Post created successfully", HttpStatus.OK, postDto.content(), 200));
    }

    @Override
    public ResponseEntity<ApiResponse<String>> reportPost(UUID postId, String reportReason) throws MindConnectException {
        String email = UserUtil.getLoggedInUser();
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new MindConnectException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MindConnectException("Post not found"));

        if (reportReason == null || reportReason.isEmpty()) {
            throw new MindConnectException("Failed to report post: Report reason is missing");
        }

        boolean alreadyReported = reportRepository.existsByReportedByAndPost(user, post);
        if (alreadyReported) {
            throw new MindConnectException("You have already reported this post");
        }

        Report report = new Report();
        report.setReportedBy(user);
        report.setPost(post);
        report.setReason(reportReason);

        reportRepository.save(report);

        post.setReportCount(post.getReportCount() + 1);

        postRepository.save(post);

       // return ResponseEntity.ok(new ApiResponse<>("Post reported successfully", HttpStatus.OK));
        return ResponseEntity.ok(new ApiResponse<>("Post created successfully", HttpStatus.OK, post.getContent(), 200));

    }

    @Override
    public ResponseEntity<ApiResponse<String>> updatePost(UUID postId, PostDTO postDTO) throws MindConnectException {
        String email = UserUtil.getLoggedInUser();
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new MindConnectException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MindConnectException("Post not found with id: " + postId));

        if (!post.getUser().equals(user)) {
            throw new MindConnectException("You are not authorized to update this post");
        }

        if (postDTO.content() != null && !postDTO.content().isEmpty()) {
            post.setContent(postDTO.content());
        } else {
            throw new MindConnectException("Post content cannot be empty");
        }

        postRepository.save(post);

        return ResponseEntity.ok(new ApiResponse<>("Post updated successfully", HttpStatus.OK));
    }


    @Override
    public ResponseEntity<ApiResponse<String>> deletePost(UUID postId) throws MindConnectException {
        String email = UserUtil.getLoggedInUser();
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new MindConnectException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MindConnectException("Post not found with id: " + postId));

        if (!post.getUser().equals(user)) {
            throw new MindConnectException("You are not authorized to delete this post");
        }

        List<Report> reports = reportRepository.findByPost(post);
        reports.forEach(report -> reportRepository.delete(report));

        postRepository.delete(post);

        return ResponseEntity.ok(new ApiResponse<>("Post deleted successfully", HttpStatus.OK));
    }


}


