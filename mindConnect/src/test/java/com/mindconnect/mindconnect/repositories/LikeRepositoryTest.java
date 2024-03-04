package com.mindconnect.mindconnect.repositories;

import com.mindconnect.mindconnect.Models.Comment;
import com.mindconnect.mindconnect.Models.Like;
import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.Models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;


/**
 * For the purpose of this test, am going to use the triple A method:
 * Arrange: Make the necessary arrangement for what I want to test for
 * Act: Act on the arranged items
 * Assert: Make certain assertions on what needs to be done
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class LikeRepositoryTest {
    @Autowired
    private LikeRepository likeRepository;

    Post post;
    User user;
    Comment comment;

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
    }

    @Test
    void shouldSaveLikeWithPost() {
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);

        Assertions.assertNotNull(savedLike);
        Assertions.assertNotNull(savedLike.getPost());
    }

    @Test
    void shouldDeleteLikeWithPost() {
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);
        likeRepository.delete(savedLike);

        Assertions.assertTrue(likeRepository.findById(savedLike.getId()).isEmpty());
    }

    @Test
    void shouldSaveLikeWithComment() {
        Like like = new Like();
        like.setComment(comment);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);

        Assertions.assertNotNull(savedLike);
        Assertions.assertNotNull(savedLike.getComment());
    }

    @Test
    void shouldDeleteLikeWithComment() {
        Like like = new Like();
        like.setComment(comment);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);
        likeRepository.delete(savedLike);

        Assertions.assertTrue(likeRepository.findById(savedLike.getId()).isEmpty());
    }
}