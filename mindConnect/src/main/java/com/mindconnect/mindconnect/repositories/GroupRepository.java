package com.mindconnect.mindconnect.repositories;

import com.mindconnect.mindconnect.Models.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    Optional<Group> findByNameIgnoreCase(@NonNull String name);
    boolean existsByNameIgnoreCase(@NonNull String name);
    @Query(value = "SELECT  g.name, g.about, COALESCE(COUNT(ug.user_id), 0) AS users , COALESCE(COUNT(p.group_id), 0) As posts , CONCAT(u.first_name, ' ', u.last_name) AS adminName , g.created_at " +
            "FROM group_list g " +
            "LEFT JOIN posts p on g.id = p.group_id " +
            "LEFT JOIN user_group ug on g.id = ug.group_id " +
            "LEFT JOIN users u on g.admin_id = u.id " +
            "GROUP BY g.name, g.about, CONCAT(u.first_name, ' ', u.last_name), g.created_at " +
            "ORDER BY COALESCE(COUNT(ug.user_id), 0) DESC ", nativeQuery = true)
    Page<Object[]> getGroupList(Pageable pageable);

    Optional<Group> findByNameIgnoreCase(@NonNull String name);

}
