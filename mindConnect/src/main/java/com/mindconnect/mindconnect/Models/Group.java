package com.mindconnect.mindconnect.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity(name = "groups")
@Table(name = "groups")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "about", columnDefinition = "TEXT")
    private String about;

    @ManyToOne
    @JoinColumn(
            name = "admin_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "group_admin_fkey")
    )
    private User admin;

    @OneToMany(mappedBy = "group")
    private List<Post> posts;

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(
                    name = "group_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            )

    )
    private Set<User> users;

}
