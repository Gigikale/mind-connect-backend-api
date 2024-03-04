package com.mindconnect.mindconnect.repositories;

import com.mindconnect.mindconnect.Models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    Optional<Like> findByUser_IdAndPost_Id(@NonNull UUID id, @NonNull UUID id1);

    Optional<Like> findByUser_IdAndComment_Id(@NonNull UUID id, @NonNull UUID id1);

    @Query("SELECT COUNT(l) FROM likes l WHERE l.post.id = :postId")
    Long getLikeCountByPostId(UUID postId);
}