package com.mindconnect.mindconnect.repositories;

import com.mindconnect.mindconnect.Models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    boolean existsByNameIgnoreCase(@NonNull String name);
}
