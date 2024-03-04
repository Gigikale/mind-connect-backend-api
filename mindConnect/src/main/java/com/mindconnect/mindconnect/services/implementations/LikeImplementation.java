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
import com.mindconnect.mindconnect.services.LikeService;
import com.mindconnect.mindconnect.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeImplementation implements LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Override
    public ApiResponse<String> likePost(UUID post) {
        User loggedInUser = userRepository.findByEmailAddress(UserUtil.getLoggedInUser())
                .orElseThrow(() -> new MindConnectException("User not found"));
        Post existingPost = postRepository.findById(post)
                .orElseThrow(() -> new MindConnectException("Post not found"));
        Optional<Like> existingLike = likeRepository.findByUser_IdAndPost_Id(
                loggedInUser.getId(), existingPost.getId()
        );
        if(existingLike.isPresent()) {
            existingLike.ifPresent(likeRepository::delete);
            return new ApiResponse<>("Post unliked", HttpStatus.OK);
        } else {
            Like like = new Like();
            like.setPost(existingPost);
            like.setUser(loggedInUser);
            likeRepository.save(like);
            return new ApiResponse<>("Post liked", HttpStatus.OK);
        }
    }

    @Override
    public ApiResponse<String> likeComment(UUID comment) {
        User loggedInUser = userRepository.findByEmailAddress(UserUtil.getLoggedInUser())
                .orElseThrow(() -> new MindConnectException("User not found"));
        Comment existingComment = commentRepository.findById(comment)
                .orElseThrow(() -> new MindConnectException("Comment not found"));
        Optional<Like> existingLike = likeRepository.findByUser_IdAndComment_Id(
                loggedInUser.getId(), existingComment.getId()
        );
        if(existingLike.isPresent()) {
            existingLike.ifPresent(likeRepository::delete);
            return new ApiResponse<>("Comment unliked", HttpStatus.OK);
        } else {
            Like like = new Like();
            like.setComment(existingComment);
            like.setUser(loggedInUser);
            likeRepository.save(like);
            return new ApiResponse<>("Comment liked", HttpStatus.OK);
        }
    }
}
