package com.mindconnect.mindconnect.repositories;

import com.mindconnect.mindconnect.Models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmTokenRepository extends JpaRepository<ConfirmationToken, Long> {
}
