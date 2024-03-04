package com.mindconnect.mindconnect.repositories;

import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.Models.Report;
import com.mindconnect.mindconnect.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID>{
    boolean existsByReportedByAndPost(User reportedBy, Post post);

    List<Report> findByPost(Post post);

}
